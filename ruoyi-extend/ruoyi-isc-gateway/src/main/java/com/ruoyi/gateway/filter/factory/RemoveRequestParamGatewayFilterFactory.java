package com.ruoyi.gateway.filter.factory;

import cn.hutool.json.JSONObject;
import com.ruoyi.gateway.exception.GatewayException;
import com.ruoyi.gateway.utils.GatewayUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;
import static org.springframework.util.CollectionUtils.unmodifiableMultiValueMap;

/**
 * @author Wenchao Gong
 * @date 2021-10-15
 */
public class RemoveRequestParamGatewayFilterFactory extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

    public RemoveRequestParamGatewayFilterFactory()
    {
        super(NameConfig.class);
    }

    @Override
    public List<String> shortcutFieldOrder()
    {
        return Arrays.asList(NAME_KEY);
    }

    @Override
    public GatewayFilter apply(NameConfig config)
    {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
            {
                ServerHttpRequest request = exchange.getRequest();
                HttpMethod method = request.getMethod();
                if (HttpMethod.GET.equals(method))
                {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>(request.getQueryParams());
                    queryParams.remove(config.getName());

                    URI newUri = UriComponentsBuilder.fromUri(request.getURI())
                            .replaceQueryParams(unmodifiableMultiValueMap(queryParams)).build().toUri();

                    ServerHttpRequest updatedRequest = exchange.getRequest().mutate().uri(newUri).build();

                    return chain.filter(exchange.mutate().request(updatedRequest).build());
                } else if (HttpMethod.POST.equals(method))
                {
                    ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
                    Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                            .flatMap(body -> {
                                MediaType mediaType = request.getHeaders().getContentType();
                                if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType))
                                {
                                    JSONObject jsonObject = new JSONObject(body);
                                    jsonObject.remove(config.getName());
                                    return Mono.just(jsonObject.toString());
                                } else if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType))
                                {
                                    if (StringUtils.hasText(body))
                                    {
                                        return Mono.just(Arrays.stream(body.split("&"))
                                                .filter(str -> config.getName().equals(str.split("=")[0]))
                                                .collect(Collectors.joining("&")));
                                    }
                                    return Mono.just(body);
                                }
                                return Mono.empty();
                            });
                    return GatewayUtils.modifyBody(exchange, chain, modifiedBody);
                } else
                {
                    throw new GatewayException(String.format("Method %s Not Supported!", method.name()));
                }
            }

            @Override
            public String toString()
            {
                return filterToStringCreator(RemoveRequestParamGatewayFilterFactory.this)
                        .append("name", config.getName()).toString();
            }
        };
    }
}
