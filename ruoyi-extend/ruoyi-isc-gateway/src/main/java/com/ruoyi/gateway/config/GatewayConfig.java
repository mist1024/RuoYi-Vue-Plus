package com.ruoyi.gateway.config;

import cn.hutool.extra.spring.SpringUtil;
import com.ruoyi.gateway.config.handler.GlobalErrorWebExceptionHandler;
import com.ruoyi.gateway.config.provider.RedisRouteDefinitionRepository;
import com.ruoyi.gateway.filter.CustomerGlobalFilter;
import com.ruoyi.gateway.ratelimit.CustomerRedisRateLimiter;
import com.ruoyi.gateway.utils.beans.TopicMsg;
import com.ruoyi.gateway.utils.caching.CachingRule;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.SerializationCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.ConfigurationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * Gateway 配置文件
 *
 * @author Wenchao Gong
 * @date 2021-09-11
 */
@Slf4j
@Configuration
public class GatewayConfig implements ApplicationEventPublisherAware {
    public static final Codec TOPIC_MSG_CODES_INSTANCE = new TypedJsonJacksonCodec(String.class, TopicMsg.class);
    public static final String TOPIC_GATEWAY_REFRESH_ROUTE = "TOPIC_GATEWAY_REFRESH_ROUTE";
    public static final String TOPIC_GATEWAY_RULE = "TOPIC_GATEWAY_RULE";
    public ApplicationEventPublisher publisher;
    private final RedissonClient client = SpringUtil.getBean(RedissonClient.class);

    {
        subscribe(TOPIC_GATEWAY_REFRESH_ROUTE, TopicMsg.class, msg -> {
            publisher.publishEvent(new RefreshRoutesEvent(this));
            log.info("网关接收通知[{}]刷新本地路由!", msg);
        });
        subscribe(TOPIC_GATEWAY_RULE, TopicMsg.class, msg -> {
            switch (msg.getType()) {
                case ADD:
                case UPDATE:
                case DELETE:
                    CachingRule.evict(msg.getId());
                    log.info("网关接收通知id:[{}], type:[{}], msg:[{}]清除本地规则!", msg.getId(), msg.getType(), msg.getMsg());
                    break;
                case REFRESH:
                    CachingRule.refresh();
                    log.info("网关接收通知id:[{}], type:[{}], msg:[{}]本地所有规则!", msg.getId(), msg.getType(), msg.getMsg());
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * Redis 路由仓库
     *
     * @return redis路由仓库
     */
    @Bean
    public RouteDefinitionRepository redisRouteDefinitionRepository() {
        return new RedisRouteDefinitionRepository(client);
    }

    @Bean
    public CustomerRedisRateLimiter customerRedisRateLimiter(ReactiveStringRedisTemplate redisTemplate,
                                                             @Qualifier(RedisRateLimiter.REDIS_SCRIPT_NAME) RedisScript<List<Long>> redisScript,
                                                             ConfigurationService configurationService, DefaultRedisScript<Long> timeRedisScript) {
        return new CustomerRedisRateLimiter(redisTemplate, redisScript, configurationService, timeRedisScript);
    }

    @Bean
    public GlobalFilter customerGlobalFilter(CustomerRedisRateLimiter customerRedisRateLimiter) {
        return new CustomerGlobalFilter(customerRedisRateLimiter);
    }

    @Bean
    public ErrorWebExceptionHandler errorWebExceptionHandler() {
        return new GlobalErrorWebExceptionHandler();
    }

    /**
     * 订阅通道接收消息
     *
     * @param channelKey 通道key
     * @param clazz      消息类型
     * @param consumer   自定义处理
     */
    public <T> void subscribe(String channelKey, Class<T> clazz, Consumer<T> consumer) {
        RTopic topic = client.getTopic(channelKey, new SerializationCodec());
        topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
}
