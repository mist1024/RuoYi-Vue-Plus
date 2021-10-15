package com.ruoyi.gateway.filter.factory;

import cn.hutool.json.JSONObject;
import com.ruoyi.gateway.exception.GatewayException;
import com.ruoyi.gateway.utils.GatewayUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * @author Wenchao Gong
 * @date 2021-10-04
 */
public class AddRequestParamGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config)
    {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
            {
                final ServerHttpRequest request = exchange.getRequest();
                HttpMethod method = request.getMethod();
                if (HttpMethod.GET.equals(method))
                {
                    URI uri = request.getURI();
                    StringBuilder query = new StringBuilder();
                    String originalQuery = uri.getRawQuery();

                    if (StringUtils.hasText(originalQuery))
                    {
                        query.append(originalQuery);
                        if (originalQuery.charAt(originalQuery.length() - 1) != '&')
                        {
                            query.append('&');
                        }
                    }

                    String value = ServerWebExchangeUtils.expand(exchange, config.getValue());
                    // TODO urlencode?
                    query.append(config.getName());
                    query.append('=');
                    query.append(value);

                    try
                    {
                        URI newUri = UriComponentsBuilder.fromUri(uri).replaceQuery(query.toString()).build(false).toUri();
                        return chain.filter(exchange.mutate().request(request.mutate().uri(newUri).build()).build());
                    } catch (RuntimeException ex)
                    {
                        throw new IllegalStateException("Invalid URI query: \"" + query.toString() + "\"");
                    }
                } else if (HttpMethod.POST.equals(method))
                {
                    ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
                    Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                            .flatMap(body -> {
                                MediaType mediaType = request.getHeaders().getContentType();
                                if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType))
                                {
                                    JSONObject jsonObject = new JSONObject(body);
                                    jsonObject.set(config.getName(), config.getValue());
                                    return Mono.just(jsonObject.toString());
                                } else if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType))
                                {
                                    StringBuilder newBody = new StringBuilder();
                                    if (org.springframework.util.StringUtils.hasText(body))
                                    {
                                        newBody.append(body);
                                        if (body.charAt(body.length() - 1) != '&')
                                        {
                                            newBody.append('&');
                                        }
                                    }
                                    String value = ServerWebExchangeUtils.expand(exchange, config.getValue());
                                    newBody.append(config.getName());
                                    newBody.append('=');
                                    newBody.append(value);
                                    return Mono.just(newBody.toString());
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
                return filterToStringCreator(AddRequestParamGatewayFilterFactory.this)
                        .append(config.getName(), config.getValue()).toString();
            }
        };
    }

}
