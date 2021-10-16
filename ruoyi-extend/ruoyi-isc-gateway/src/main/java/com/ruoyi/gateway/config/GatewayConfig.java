package com.ruoyi.gateway.config;

import com.ruoyi.gateway.config.provider.RedisRouteDefinitionRepository;
import com.ruoyi.gateway.filter.CustomerGlobalFilter;
import com.ruoyi.gateway.ratelimit.CustomerRedisRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.ConfigurationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.List;

/**
 * Gateway 配置文件
 *
 * @author Wenchao Gong
 * @date 2021-09-11
 */
@Configuration
public class GatewayConfig
{

    @Resource
    private RedisTemplate<String, RouteDefinition> redisTemplate;
    @Resource
    private RedissonClient redissonClient;

    @Bean
    public RedisTemplate<String, RouteDefinition> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        final RedisTemplate<String, RouteDefinition> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<RouteDefinition> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(RouteDefinition.class);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        return template;
    }

    /**
     * Redis 路由仓库
     *
     * @return redis路由仓库
     */
    @Bean
    public RouteDefinitionRepository redisRouteDefinitionRepository()
    {
        return new RedisRouteDefinitionRepository(redissonClient);
    }

    @Bean
    public CustomerRedisRateLimiter customerRedisRateLimiter(ReactiveStringRedisTemplate redisTemplate,
             @Qualifier(RedisRateLimiter.REDIS_SCRIPT_NAME) RedisScript<List<Long>> redisScript,
             ConfigurationService configurationService) {
        return new CustomerRedisRateLimiter(redisTemplate, redisScript, configurationService);
    }

    @Bean
    public GlobalFilter customerGlobalFilter(CustomerRedisRateLimiter customerRedisRateLimiter)
    {
        return new CustomerGlobalFilter(customerRedisRateLimiter);
    }
}
