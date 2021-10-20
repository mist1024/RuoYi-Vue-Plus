package com.ruoyi.gateway.filter;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.gateway.exception.*;
import com.ruoyi.gateway.ratelimit.CustomerRedisRateLimiter;
import com.ruoyi.gateway.utils.GatewayUtils;
import com.ruoyi.gateway.utils.beans.IscRule;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.ruoyi.gateway.filter.CustomerGlobalFilter.AccessKey.AccessKeyType;
import static com.ruoyi.gateway.filter.CustomerGlobalFilter.AccessKey.AccessKeyType.*;
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

    private static final String ACCESS_KEY_NAME_DEFAULT = "ak";
    private static final TimeUnit[] TIME_UNITS = {TimeUnit.SECONDS, TimeUnit.MINUTES, TimeUnit.HOURS, TimeUnit.DAYS};

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final Route route = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        final Map<String, Object> metadata = route.getMetadata();
        final ServerHttpRequest request = exchange.getRequest();
        //ak 是否存在
        final HttpMethod httpMethod = request.getMethod();
        AccessKey accessKey = new AccessKey(String.valueOf(metadata.getOrDefault(GatewayUtils.CONFIG_ACCESS_KEY_NAME_KEY,
                ACCESS_KEY_NAME_DEFAULT)));
        accessKey.set(GatewayUtils.getValue(null, () -> request.getHeaders().get(accessKey.name)), HEADER);
        MultiValueMap<String, String> queryParams = request.getQueryParams().containsKey(accessKey.name) ?
                new LinkedMultiValueMap<>(request.getQueryParams()) : request.getQueryParams();
        if (HttpMethod.GET.equals(httpMethod)) {
            return handleGetRequest(exchange, chain, route, request, accessKey, queryParams);
        } else if (HttpMethod.POST.equals(httpMethod)) {
            return handlePostRequest(exchange, chain, route, request, accessKey, queryParams);
        }
        throw new MethodNotSupportedException(httpMethod);
    }

    /**
     * 处理POST 请求
     *
     * @param exchange    当前服务交换器
     * @param chain       当前过滤链
     * @param route       路由信息
     * @param request     请求信息
     * @param accessKey   AK信息
     * @param queryParams URL查询参数
     * @return 指示请求处理何时完成
     */
    private Mono<Void> handlePostRequest(ServerWebExchange exchange, GatewayFilterChain chain, Route route,
                                         ServerHttpRequest request, AccessKey accessKey,
                                         MultiValueMap<String, String> queryParams) {
        //处理 URL AccessKey
        handleAccessKey(accessKey, () -> queryParams.get(accessKey.name), URI, false);
        ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
        final Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).defaultIfEmpty(StrUtil.EMPTY)
            .flatMap(body -> {
                MediaType mediaType = request.getHeaders().getContentType();
                if (Objects.isNull(mediaType) || MediaType.APPLICATION_JSON.equals(mediaType)) {
                    try {
                        JSONObject jsonObj = StrUtil.isBlank(body) ? new JSONObject() : JSONUtil.parseObj(body);
                        return handlePostRequestJson(exchange, route, request, accessKey, jsonObj, queryParams);
                    }catch (JSONException e) {
                        if(Objects.nonNull(mediaType)) {
                            return Mono.error(e);
                        }
                    }
                } else if (MediaType.APPLICATION_FORM_URLENCODED.equals(mediaType)) {
                    return handlePostRequestFormUrlencoded(exchange, route, request, accessKey, body, queryParams);
                }
                return Mono.error(() -> new ContentTypeNotSupportedException(mediaType));
            });
        //如果 AccessKey 在URI部分 URI需要重新生成
        URI uri = URI.equals(accessKey.type) ? getNewUri().apply(request, queryParams) : null;
        return GatewayUtils.modifyBody(exchange, chain, modifiedBody, uri);
    }

    /**
     * 处理POST APPLICATION_FORM_URLENCODED 请求
     *
     * @param exchange      当前服务交换器
     * @param route         路由信息
     * @param request       请求信息
     * @param accessKey     AK信息
     * @param body          请求Body信息
     * @return 处理后的Body信息
     */
    private Mono<? extends String> handlePostRequestFormUrlencoded(ServerWebExchange exchange, Route route,
                                                                   ServerHttpRequest request, AccessKey accessKey,
                                                                   String body, MultiValueMap<String, String> queryParams) {

        if (StrUtil.isBlank(body)) {
            Assert.notBlank(accessKey.value, () -> new AkRequireException(accessKey.name));
        }
        final List<String[]> srcParams = Arrays.stream(body.split("&")).map(param -> param.split("="))
            .collect(Collectors.toList());
        final IscRule rule = handleRule(accessKey, () -> srcParams.stream()
            .filter(param -> param.length > 0 && accessKey.name.equals(param[0]))
            .map(param -> param[1]).collect(Collectors.toList()), route, BODY);
        Supplier<Mono<String>> rateLimiterAfterSupplier = () -> {
            removeParam(accessKey, request, null, queryParams);
            final List<String[]> params = srcParams.stream().filter(param -> !accessKey.name.equals(param[0]))
                .collect(Collectors.toList());
            handleHiddenParams(route, params, (next, list) -> {
                Object value;
                if (Objects.isNull(value = next.getValue())) {
                    list.add(new String[]{next.getKey(), StrUtil.EMPTY});
                } else if (value instanceof JSONArray) {
                    ((JSONArray) value).stream().map(String::valueOf)
                        .forEach(v -> list.add(new String[]{next.getKey(), v}));
                } else {
                    list.add(new String[]{next.getKey(), next.getValue().toString()});
                }
            });
            return Mono.just(params.stream().map(param -> param[0] + '=' + param[1]).collect(Collectors.joining("&")));
        };

        return rateLimiter(exchange, rule, route, 0, rateLimiterAfterSupplier);
    }

    /**
     * 处理POST APPLICATION_JSON 请求
     *
     * @param exchange      当前服务交换器
     * @param route         路由信息
     * @param request       请求信息
     * @param accessKey     AK信息
     * @param jsonObj       请求Body JSON信息
     * @return 处理后的Body信息
     */
    private Mono<? extends String> handlePostRequestJson(ServerWebExchange exchange, Route route,
                                                         ServerHttpRequest request, AccessKey accessKey,
                                                         JSONObject jsonObj, MultiValueMap<String, String> queryParams) {
        final IscRule rule = handleRule(accessKey, () -> Collections.singletonList(jsonObj.get(accessKey.name,
            String.class, true)), route, BODY);
        Supplier<Mono<String>> rateLimiterAfterSupplier = () -> {
            removeParam(accessKey, request, () -> jsonObj.remove(accessKey.name), queryParams);
            handleHiddenParams(route, jsonObj, (next, map) -> map.set(next.getKey(), next.getValue()));
            return Mono.just(jsonObj.toString());
        };

        return rateLimiter(exchange, rule, route, 0, rateLimiterAfterSupplier);
    }

    /**
     * 处理GET 请求
     *
     * @param exchange      当前服务交换器
     * @param chain         当前过滤链
     * @param route         路由信息
     * @param request       请求信息
     * @param accessKey     AK信息
     * @param queryParams   URL查询参数
     * @return 指示请求处理何时完成
     */
    private Mono<Void> handleGetRequest(ServerWebExchange exchange, GatewayFilterChain chain, Route route,
                                        ServerHttpRequest request, AccessKey accessKey,
                                        MultiValueMap<String, String> queryParams) {
        final IscRule rule = handleRule(accessKey, () -> queryParams.get(accessKey.name), route, URI);
        Supplier<Mono<Void>> rateLimiterAfterSupplier = () -> {
            removeParam(accessKey, request, null, queryParams);
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
            ServerHttpRequest req = exchange.getRequest().mutate().uri(getNewUri().apply(request, queryParams)).build();
            return chain.filter(exchange.mutate().request(req).build());
        };
        return rateLimiter(exchange, rule, route, 0, rateLimiterAfterSupplier);
    }

    private BiFunction<ServerHttpRequest, MultiValueMap<String, String>, URI> getNewUri() {
        return (request, queryParams) -> UriComponentsBuilder.fromUri(request.getURI())
                .replaceQueryParams(unmodifiableMultiValueMap(queryParams)).build().toUri();
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 处理规则
     *
     * @param accessKey     AK信息
     * @param valueSupplier valueList 生产者
     * @param route         路由信息
     * @param type          AK类型
     * @return AK对应服务规则信息
     */
    private IscRule handleRule(AccessKey accessKey, Supplier<List<String>> valueSupplier, Route route, AccessKeyType type) {
        if(Objects.nonNull(valueSupplier) && Objects.nonNull(type)) {
            handleAccessKey(accessKey, valueSupplier, type, true);
        }
        //获取规则
        final IscRule rule = GatewayUtils.getRequiredValue(() -> GatewayUtils.getRule(accessKey.value, route.getId()),
                () -> new RuleNotExistException(accessKey.value, route.getId()));
        //是否到期
        GatewayUtils.isBefore(rule, () -> new RuleExpiredException(rule.getExpire()));
        //设置AK 到ID 为了传参方便
        rule.setId(accessKey.value);
        return rule;
    }

    /**
     * 处理 accessKey
     *
     * @param accessKey      AccessKey
     * @param valueSupplier  valueList 生产者
     * @param type           AccessKeyType
     * @param throwException 是否抛出异常
     */
    private void handleAccessKey(AccessKey accessKey, Supplier<List<String>> valueSupplier, AccessKeyType type, boolean throwException) {
        //获取AK
        String value;
        if(throwException) {
            value = GatewayUtils.getValue(accessKey.value, valueSupplier, () -> new AkRequireException(accessKey.name));
        }else {
            value = GatewayUtils.getValue(accessKey.value, valueSupplier, null);
        }
        accessKey.set(value, type);
    }

    /**
     * 删除参数
     *
     * @param accessKey       AK信息
     * @param request        请求
     * @param removeSupplier 删除参数提供者
     * @param queryParams   URI请求参数
     * @param <T>            类型
     */
    private <T> void removeParam(AccessKey accessKey, ServerHttpRequest request, Supplier<T> removeSupplier,
                                 MultiValueMap<String, String> queryParams) {
        //如果header中有AK,则删除
        if (HEADER.equals(accessKey.type)) {
            final HttpHeaders headers = request.getHeaders();
            if(headers.containsKey(accessKey.name)) {
                request.mutate().headers(headMap -> headMap.remove(accessKey.name)).build();
            }
        }else if(URI.equals(accessKey.type)) {
            queryParams.remove(accessKey.name);
        }
        if (Objects.nonNull(removeSupplier)) {
            removeSupplier.get();
        }
    }

    /**
     * 处理隐藏参数
     *
     * @param route  路由信息
     * @param result 参数
     * @param mapper 处理参数方法
     * @param <U>    参数类型
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
        for (Map.Entry<String, Object> next : json.entrySet()) {
            mapper.accept(next, result);
        }
    }

    /**
     * 限流
     *
     * @param exchange                 当前服务交换器
     * @param rule                     限流规则
     * @param route                    路由
     * @param rateLimiterAfterSupplier 限流后操作(删除参数、添加隐藏参数，跳转)
     * @param <T>                      最终返回类型
     * @return rateLimiterAfterSupplier 返回类型
     */
    private <T> Mono<T> rateLimiter(ServerWebExchange exchange, IscRule rule, Route route, final int index,
                                    Supplier<Mono<T>> rateLimiterAfterSupplier) {
        final TimeUnit timeUnit = TIME_UNITS[index];
        final Long limit = TimeUnit.SECONDS.equals(timeUnit) ? rule.getSecondsLimit() : TimeUnit.MINUTES.equals(timeUnit)
            ? rule.getMinutesLimit() : TimeUnit.HOURS.equals(timeUnit) ? rule.getHoursLimit() : rule.getDaysLimit();
        if (Objects.isNull(limit) || limit <= 0L) {
            if (TimeUnit.DAYS.equals(timeUnit)) {
                return rateLimiterAfterSupplier.get();
            }
            return rateLimiter(exchange, rule, route, index + 1, rateLimiterAfterSupplier);
        }
        return rateLimiter.isAllowed(route.getId(), rule.getId(), limit, timeUnit).flatMap(response -> {
            for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
                exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
            }
            if (response.isAllowed()) {
                if (TimeUnit.DAYS.equals(timeUnit)) {
                    return rateLimiterAfterSupplier.get();
                }
                return rateLimiter(exchange, rule, route, index + 1, rateLimiterAfterSupplier);
            }
            return Mono.error(() -> new RateLimitException(limit, timeUnit));
        });
    }

    /**
     * AK 信息
     */
    public static class AccessKey {
        public AccessKey(String name) {
            this.name = name;
        }
        public void set(String value, AccessKeyType type) {
            if(StrUtil.isNotBlank(value)) {
                this.value = value;
                this.type = type;
            }
        }
        public enum AccessKeyType {
            HEADER,
            URI,
            BODY
        }
        private final String name;
        private String value;
        private AccessKeyType type;
    }
}
