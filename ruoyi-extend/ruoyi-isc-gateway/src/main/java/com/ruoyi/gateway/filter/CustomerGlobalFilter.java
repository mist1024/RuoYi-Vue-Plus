package com.ruoyi.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.gateway.utils.GatewayUtils;
import com.ruoyi.gateway.utils.beans.IscRule;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            handleRule(headerAk, () -> queryParams.get(accessKeyName), route, request, accessKeyName);
            queryParams.remove(accessKeyName);
            handleHiddenParams(route, queryParams, (json, map) -> {
                Iterator<Map.Entry<String, Object>> iterator = json.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> next = iterator.next();
                    Object value;
                    if (Objects.isNull(value = next.getValue()) || StrUtil.isBlank(value.toString())) {
                        map.add(next.getKey(), StrUtil.EMPTY);
                    } else {
                        map.add(next.getKey(), value.toString());
                    }
                }
            });
            URI newUri = UriComponentsBuilder.fromUri(request.getURI())
                    .replaceQueryParams(unmodifiableMultiValueMap(queryParams)).build().toUri();
            ServerHttpRequest updatedRequest = exchange.getRequest().mutate().uri(newUri).build();
            return chain.filter(exchange.mutate().request(updatedRequest).build());
        } else if (HttpMethod.POST.equals(httpMethod)) {
            final ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
            final Mono<String> modifiedBody = serverRequest.bodyToMono(String.class);
            modifiedBody.flatMap(body -> {
                MediaType mediaType = request.getHeaders().getContentType();
                if (MediaType.APPLICATION_JSON.equals(mediaType)) {
                    JSONObject jsonObj = JSONUtil.parseObj(body);
                    String ak = handleRule(headerAk, () -> Arrays.asList(jsonObj.get(accessKeyName, String.class,
                            true)), route, request, accessKeyName);
                    jsonObj.remove(accessKeyName);
                    handleHiddenParams(route, jsonObj, (json, map) -> {
                        Iterator<Map.Entry<String, Object>> iterator = json.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, Object> next = iterator.next();
                            map.set(next.getKey(), next.getValue());
                        }
                    });
                    return Mono.just(jsonObj.toString());
                } else if (MediaType.APPLICATION_FORM_URLENCODED.equals(mediaType)) {
                    if (StringUtils.hasText(body)) {
                        final Stream<String[]> stream = Arrays.stream(body.split("&")).map(param -> param.split("="));
                        handleRule(headerAk, () -> stream.filter(param -> param.length > 0 &&
                                        accessKeyName.equals(param[0])).map(param -> param[1]).collect(Collectors.toList()),
                                route, request, accessKeyName);
                        final List<String[]> params = stream.filter(param -> !accessKeyName.equals(param[0])).collect(Collectors.toList());
                        handleHiddenParams(route, params, (json, list) -> {
                            Iterator<Map.Entry<String, Object>> iterator = json.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Map.Entry<String, Object> next = iterator.next();
                                list.add(new String[]{next.getKey(), next.getValue().toString()});
                            }
                        });
                        return Mono.just(params.stream().map(param -> param[0] + '=' + param[1]).collect(Collectors.joining("&")));
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
     * @param request
     * @param accessKeyName
     * @return
     */
    private String handleRule(String headerAk, Supplier<List<String>> valueSupplier, Route route,
                              ServerHttpRequest request, String accessKeyName) {
        //获取AK
        final String ak = GatewayUtils.getValue(headerAk, valueSupplier, () -> new RuntimeException("AK 不存在"));
        //获取规则
        final IscRule rule = GatewayUtils.getRequiredValue(() -> GatewayUtils.getRule(ak, route.getId()),
                () -> new RuntimeException("AK异常"));
        //是否到期
        GatewayUtils.isBefore(rule, () -> new RuntimeException("AK已过期"));
        //TODO 限流

        //如果header中有AK,则删除
        if (Objects.nonNull(headerAk)) {
            request.getHeaders().remove(accessKeyName);
        }
        return ak;
    }

    /**
     * 处理隐藏参数
     *
     * @param route
     * @param result
     * @param mapper
     * @param <U>
     */
    private <U> void handleHiddenParams(Route route, U result, BiConsumer<JSONObject, U> mapper) {
        final Object obj = route.getMetadata().get(GatewayUtils.CONFIG_ADD_PARAM_KEY);
        if (Objects.isNull(obj)) {
            return;
        }
        final JSONObject json = JSONUtil.parseObj(obj.toString());
        if (json.isEmpty()) {
            return;
        }
        mapper.accept(json, result);
    }
}
