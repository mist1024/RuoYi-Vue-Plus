package com.ruoyi.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.gateway.ratelimit.CustomerRedisRateLimiter;
import com.ruoyi.gateway.utils.GatewayUtils;
import com.ruoyi.gateway.utils.beans.IscRule;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;
import static org.springframework.util.CollectionUtils.unmodifiableMultiValueMap;

/**
 * 自定义全局过滤器，操作如下：
 * 1.ak 是否存在
 * 2.是否到期
 * 3.是否限流
 * 4.删除参数ak
 * 5.添加隐藏参数
 *
 * @author Wenchao Gong
 * @date 2021-10-15
 */
public class CustomerGlobalFilter implements GlobalFilter, Ordered {
    private final CustomerRedisRateLimiter rateLimiter;
    public CustomerGlobalFilter(CustomerRedisRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }
    public static final TimeUnit[] TIME_UNITS = {TimeUnit.SECONDS, TimeUnit.MINUTES, TimeUnit.HOURS, TimeUnit.DAYS};

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final Route route = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        final Map<String, Object> metadata = route.getMetadata();
        final ServerHttpRequest request = exchange.getRequest();
        //ak 是否存在
        final HttpMethod httpMethod = request.getMethod();
        final String accessKeyName = String.valueOf(metadata.get(GatewayUtils.CONFIG_ACCESS_KEY_NAME_KEY));
        String headerAk = GatewayUtils.getValue(null, () -> request.getHeaders().get(accessKeyName));
        if (HttpMethod.GET.equals(httpMethod)) {
            final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>(request.getQueryParams());
            final IscRule rule = handleRule(headerAk, () -> queryParams.get(accessKeyName), route);
            Supplier<Mono<Void>> rateLimiterAfterSupplier = () -> {
                removeParam(headerAk, request, accessKeyName, () -> queryParams.remove(accessKeyName));
                handleHiddenParams(route, queryParams, (next, map) -> {
                    Object value;
                    if (Objects.isNull(value = next.getValue())) {
                        map.add(next.getKey(), StrUtil.EMPTY);
                    } else if (value instanceof JSONArray) {
                        map.put(next.getKey(), ((JSONArray) value).toList(String.class));
                    } else {
                        map.add(next.getKey(), value.toString());
                    }
                });
                URI newUri = UriComponentsBuilder.fromUri(request.getURI())
                        .replaceQueryParams(unmodifiableMultiValueMap(queryParams)).build().toUri();
                ServerHttpRequest updatedRequest = exchange.getRequest().mutate().uri(newUri).build();
                return chain.filter(exchange.mutate().request(updatedRequest).build());
            };

            return rateLimiter(exchange, rule, route, 0, rateLimiterAfterSupplier);
        } else if (HttpMethod.POST.equals(httpMethod)) {
            final ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
            final Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap(body -> {
                MediaType mediaType = request.getHeaders().getContentType();
                if (MediaType.APPLICATION_JSON.equals(mediaType)) {
                    JSONObject jsonObj = JSONUtil.parseObj(body);
                    final IscRule rule = handleRule(headerAk, () ->
                            Arrays.asList(jsonObj.get(accessKeyName, String.class, true)), route);
                    Supplier<Mono<String>> rateLimiterAfterSupplier = () -> {
                        removeParam(headerAk, request, accessKeyName, () -> jsonObj.remove(accessKeyName));
                        handleHiddenParams(route, jsonObj, (next, map) -> {
                            map.set(next.getKey(), next.getValue());
                        });
                        return Mono.just(jsonObj.toString());
                    };

                    return rateLimiter(exchange, rule, route, 0, rateLimiterAfterSupplier);
                } else if (MediaType.APPLICATION_FORM_URLENCODED.equals(mediaType)) {
                    if (StringUtils.hasText(body)) {
                        final Stream<String[]> stream = Arrays.stream(body.split("&")).map(param -> param.split("="));
                        final IscRule rule = handleRule(headerAk, () -> stream.filter(param -> param.length > 0 &&
                                        accessKeyName.equals(param[0])).map(param -> param[1]).collect(Collectors.toList()), route);
                        Supplier<Mono<String>> rateLimiterAfterSupplier = () -> {
                            removeParam(headerAk, request, accessKeyName, null);
                            final List<String[]> params = stream.filter(param -> !accessKeyName.equals(param[0])).collect(Collectors.toList());
                            handleHiddenParams(route, params, (next, list) -> {
                                Object value;
                                if (Objects.isNull(value = next.getValue())) {
                                    list.add(new String[]{next.getKey(), StrUtil.EMPTY});
                                } else if (value instanceof JSONArray) {
                                    ((JSONArray) value).stream().map(o -> String.valueOf(o)).forEach(v -> {
                                        list.add(new String[]{next.getKey(), v});
                                    });
                                } else {
                                    list.add(new String[]{next.getKey(), next.getValue().toString()});
                                }
                            });
                            return Mono.just(params.stream().map(param -> param[0] + '=' + param[1]).collect(Collectors.joining("&")));
                        };

                        return rateLimiter(exchange, rule, route, 0, rateLimiterAfterSupplier);
                    }
                }
                return Mono.empty();
            });
            return GatewayUtils.modifyBody(exchange, chain, modifiedBody);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 处理规则
     *
     * @param headerAk
     * @param valueSupplier
     * @param route
     * @return
     */
    private IscRule handleRule(String headerAk, Supplier<List<String>> valueSupplier, Route route) {
        //获取AK
        final String ak = GatewayUtils.getValue(headerAk, valueSupplier, () -> new RuntimeException("AK 不存在"));
        //获取规则
        final IscRule rule = GatewayUtils.getRequiredValue(() -> GatewayUtils.getRule(ak, route.getId()),
                () -> new RuntimeException("AK异常"));
        //是否到期
        GatewayUtils.isBefore(rule, () -> new RuntimeException("AK已过期"));
        //设置AK 到ID 为了传参方便
        rule.setId(ak);
        return rule;
    }

    /**
     * 删除参数
     * @param headerAk
     * @param request
     * @param accessKeyName
     * @param removeSupplier
     * @param <T>
     */
    private <T> void removeParam(String headerAk, ServerHttpRequest request, String accessKeyName, Supplier<T> removeSupplier) {
        //如果header中有AK,则删除
        if (Objects.nonNull(headerAk)) {
            request.getHeaders().remove(accessKeyName);
        }
        if(Objects.nonNull(removeSupplier)) {
            removeSupplier.get();
        }
    }

    /**
     * 处理隐藏参数
     *
     * @param route
     * @param result
     * @param mapper
     * @param <U>
     */
    private <U> void handleHiddenParams(Route route, U result, BiConsumer<Map.Entry<String, Object>, U> mapper) {
        final Object obj = route.getMetadata().get(GatewayUtils.CONFIG_ADD_PARAM_KEY);
        if (Objects.isNull(obj)) {
            return;
        }
        final JSONObject json = JSONUtil.parseObj(obj.toString());
        if (json.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<String, Object>> iterator = json.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            mapper.accept(next, result);
        }
    }

    /**
     * 限流
     * @param exchange
     * @param rule
     * @param route
     * @param rateLimiterAfterSupplier 限流后操作(删除参数、添加隐藏参数，跳转)
     * @param <T>
     * @return
     */
    private <T extends Object> Mono rateLimiter(ServerWebExchange exchange, IscRule rule, Route route, final int index,
                                                Supplier<Mono<T>> rateLimiterAfterSupplier) {
        final TimeUnit timeUnit = TIME_UNITS[index];
        final Long limit = TimeUnit.SECONDS.equals(timeUnit) ? rule.getSecondsLimit() : TimeUnit.MINUTES.equals(timeUnit)
                ? rule.getMinutesLimit() : TimeUnit.HOURS.equals(timeUnit) ? rule.getHoursLimit() : rule.getDaysLimit();
        if(Objects.isNull(limit) || limit <= 0L) {
            if(TimeUnit.DAYS.equals(timeUnit)) {
                return rateLimiterAfterSupplier.get();
            }
            return rateLimiter(exchange, rule, route, index + 1, rateLimiterAfterSupplier);
        }
        return rateLimiter.isAllowed(route.getId(), rule.getId(), limit, timeUnit).flatMap(response -> {
            for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
                exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
            }
            if (response.isAllowed()) {
                if(TimeUnit.DAYS.equals(timeUnit)) {
                    return rateLimiterAfterSupplier.get();
                }
                return rateLimiter(exchange, rule, route, index + 1, rateLimiterAfterSupplier);
            }
            setResponseStatus(exchange, HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        });
    }
}
