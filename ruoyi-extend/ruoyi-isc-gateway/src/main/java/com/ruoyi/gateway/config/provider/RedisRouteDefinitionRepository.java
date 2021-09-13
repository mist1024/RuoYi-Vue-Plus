package com.ruoyi.gateway.config.provider;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Redis 路由仓库
 *
 * @author Wenchao Gong
 * @date 2021-09-11
 */
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {
    public static final String KEY_ROUTES = "ROUTES::";
    private final RedisTemplate<String, RouteDefinition> redisTemplate;

    public RedisRouteDefinitionRepository(RedisTemplate<String, RouteDefinition> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions()
    {
        List<RouteDefinition> values = redisTemplate.<String,RouteDefinition>opsForHash().values(KEY_ROUTES);
        return Flux.fromIterable(values);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route)
    {
        return route.flatMap(definition -> {
            redisTemplate.opsForHash().put(KEY_ROUTES, definition.getId(), definition);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId)
    {
        return routeId.flatMap(id -> {
            redisTemplate.opsForHash().delete(KEY_ROUTES, id);
            return Mono.empty();
        });
    }
}
