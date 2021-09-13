package com.ruoyi.gateway.config;

import com.ruoyi.gateway.config.provider.ComplexRouteDefinitionRepository;
import com.ruoyi.gateway.config.provider.RedisRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * Gateway 配置文件
 *
 * @author Wenchao Gong
 * @date 2021-09-11
 */
@Configuration
public class GatewayConfig {

    @Resource
    private RedisTemplate<String, RouteDefinition> redisTemplate;

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

    @Bean
    public RouteDefinitionRepository complexRouteDefinitionRepository()
    {
        return new ComplexRouteDefinitionRepository(inMemoryRouteDefinitionRepository(), redisRouteDefinitionRepository());
    }

    /**
     * 内存 路由仓库
     *
     * @return 本地内存路由仓库
     */
    RouteDefinitionRepository inMemoryRouteDefinitionRepository()
    {
        return new InMemoryRouteDefinitionRepository();
    }

    /**
     * Redis 路由仓库
     *
     * @return redis路由仓库
     */
    RouteDefinitionRepository redisRouteDefinitionRepository()
    {
        return new RedisRouteDefinitionRepository(redisTemplate);
    }
}
