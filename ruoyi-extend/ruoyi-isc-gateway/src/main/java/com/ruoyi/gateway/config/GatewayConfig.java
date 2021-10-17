package com.ruoyi.gateway.config;

import com.ruoyi.gateway.config.provider.RedisRouteDefinitionRepository;
import com.ruoyi.gateway.filter.CustomerGlobalFilter;
import com.ruoyi.gateway.ratelimit.CustomerRedisRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.ConfigurationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

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
    private RedissonClient redissonClient;

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
             ConfigurationService configurationService, DefaultRedisScript<Long> timeRedisScript) {
        return new CustomerRedisRateLimiter(redisTemplate, redisScript, configurationService, timeRedisScript);
    }

    @Bean
    public GlobalFilter customerGlobalFilter(CustomerRedisRateLimiter customerRedisRateLimiter)
    {
        return new CustomerGlobalFilter(customerRedisRateLimiter);
    }
}
