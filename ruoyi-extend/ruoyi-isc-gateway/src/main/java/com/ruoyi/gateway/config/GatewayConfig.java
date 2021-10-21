package com.ruoyi.gateway.config;

import cn.hutool.extra.spring.SpringUtil;
import com.ruoyi.gateway.config.handler.GlobalErrorWebExceptionHandler;
import com.ruoyi.gateway.config.provider.RedisRouteDefinitionRepository;
import com.ruoyi.gateway.filter.CustomerGlobalFilter;
import com.ruoyi.gateway.ratelimit.CustomerRedisRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
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

import javax.annotation.Resource;
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
public class GatewayConfig implements ApplicationEventPublisherAware
{
    private RedissonClient client = SpringUtil.getBean(RedissonClient.class);
    public ApplicationEventPublisher publisher;
    public static final String TOPIC_GATEWAY_REFRESH_ROUTE = "TOPIC_GATEWAY_REFRESH_ROUTE";
    {
        subscribe(TOPIC_GATEWAY_REFRESH_ROUTE, String.class, msg -> {
            publisher.publishEvent(new RefreshRoutesEvent(this));
            log.info("网关接收通知[{}]刷新本地路由!", msg);
        });
    }
    /**
     * Redis 路由仓库
     *
     * @return redis路由仓库
     */
    @Bean
    public RouteDefinitionRepository redisRouteDefinitionRepository()
    {
        return new RedisRouteDefinitionRepository(client);
    }

    @Bean
    public CustomerRedisRateLimiter customerRedisRateLimiter(ReactiveStringRedisTemplate redisTemplate,
             @Qualifier(RedisRateLimiter.REDIS_SCRIPT_NAME) RedisScript<List<Long>> redisScript,
             ConfigurationService configurationService, DefaultRedisScript<Long> timeRedisScript) {
        return new CustomerRedisRateLimiter(redisTemplate, redisScript, configurationService, timeRedisScript);
    }

    @Bean
    public GlobalFilter customerGlobalFilter(CustomerRedisRateLimiter customerRedisRateLimiter)
    {
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
        RTopic topic = client.getTopic(channelKey);
        topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher)
    {
        this.publisher = publisher;
    }
}
