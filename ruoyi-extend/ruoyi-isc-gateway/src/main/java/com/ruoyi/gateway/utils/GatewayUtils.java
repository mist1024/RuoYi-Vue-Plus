package com.ruoyi.gateway.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.extra.spring.SpringUtil;
import com.ruoyi.gateway.utils.beans.IscRule;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Wenchao Gong
 * @date 2021-10-15
 */
public class GatewayUtils {

    public static final String CONFIG_ACCESS_KEY_NAME_KEY = "accessKeyName";
    public static final String CONFIG_ADD_PARAM_KEY = "addParam";
    /**
     * Gateway 服务对应AK规则
     */
    public static final String KEY_RULES = "RULES:";

    public static final Codec RULE_CODES_INSTANCE = new TypedJsonJacksonCodec(String.class, IscRule.class);
    private static RedissonClient client = SpringUtil.getBean(RedissonClient.class);

    /**
     * 修改请求Body
     *
     * @param exchange  当前服务交换器
     * @param chain     当前过滤链
     * @param publisher body体提供者
     * @return 指示请求处理何时完成
     */
    public static Mono<Void> modifyBody(ServerWebExchange exchange, GatewayFilterChain chain, Mono<String> publisher) {
        BodyInserter bodyInserter = BodyInserters.fromPublisher(publisher, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return bodyInserter.insert(outputMessage, new BodyInserterContext())
            .then(Mono.defer(() -> {
                ServerHttpRequest request = decorate(exchange, headers, outputMessage);
                return chain.filter(exchange.mutate().request(request).build());
            }));
    }


    public static ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                                      CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0L) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set("Transfer-Encoding", "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

    /**
     * 从 values 中获取第一个不为空 的值
     *
     * @param before        获取之前的值 如果不为空直接返回
     * @param valueSupplier 值列表生产者
     * @return
     */
    public static String getValue(String before, Supplier<List<String>> valueSupplier) {
        return getValue(before, valueSupplier, null);
    }

    /**
     * 从 values 中获取第一个不为空 的值
     *
     * @param before           获取之前的值 如果不为空直接返回
     * @param valueSupplier    值列表生产者
     * @param errorMsgSupplier 异常信息生产者
     * @param <X>
     * @return
     */
    public static <X extends RuntimeException> String getValue(String before, Supplier<List<String>> valueSupplier,
                                                               Supplier<X> errorMsgSupplier) {
        if (StringUtils.hasText(before)) {
            return before;
        }
        final List<String> values = valueSupplier.get();
        if (CollectionUtil.isNotEmpty(values)) {
            for (String value : values) {
                if (StringUtils.hasText(value)) {
                    before = value;
                    break;
                }
            }
        }
        if (errorMsgSupplier != null) {
            Assert.notBlank(before, errorMsgSupplier);
        }
        return before;
    }

    /**
     * 获取必要值，可抛出异常
     *
     * @param valueSupplier
     * @param errorMsgSupplier
     * @param <T>
     * @param <X>
     * @return
     */
    public static <T, X extends RuntimeException> T getRequiredValue(Supplier<T> valueSupplier,
                                                                     Supplier<X> errorMsgSupplier) {
        final T value = valueSupplier.get();
        if (Objects.nonNull(errorMsgSupplier)) {
            Assert.notNull(value, errorMsgSupplier);
        }
        return value;
    }


    /**
     * 获取规则
     *
     * @param ak      AK
     * @param routeId 路由ID
     * @return
     */
    public static IscRule getRule(String ak, String routeId) {
        final RMap<String, IscRule> map = client.getMap(KEY_RULES, RULE_CODES_INSTANCE);
        return map.get(ak + ':' + routeId);
    }

    /**
     * 断言是否超过结束时间
     *
     * @param rule
     * @param errorMsgSupplier
     * @param <X>
     */
    public static <X extends RuntimeException> void isBefore(IscRule rule, Supplier<X> errorMsgSupplier) {
        Assert.isTrue(Objects.nonNull(rule.getExpire()) && Date.from(Instant.now()).before(rule.getExpire()),
            errorMsgSupplier);
    }
}
