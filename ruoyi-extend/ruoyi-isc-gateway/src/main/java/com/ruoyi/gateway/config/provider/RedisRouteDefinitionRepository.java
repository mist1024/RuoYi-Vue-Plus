package com.ruoyi.gateway.config.provider;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Redis 路由仓库
 *
 * @author Wenchao Gong
 * @date 2021-09-11
 */
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository
{
    public static final String KEY_ROUTES = "ROUTES::";
    private final RedissonClient redissonClient;

    public RedisRouteDefinitionRepository(RedissonClient redissonClient)
    {
        this.redissonClient = redissonClient;
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions()
    {
        RMap<String, RouteDefinition> map = redissonClient.getMap(KEY_ROUTES);
        return Flux.fromIterable(map.values());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route)
    {
        return route.flatMap(definition -> {
            RMap<String, RouteDefinition> map = redissonClient.getMap(KEY_ROUTES);
            map.put(definition.getId(), definition);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId)
    {
        return routeId.flatMap(id -> {
            RMap<String, RouteDefinition> map = redissonClient.getMap(KEY_ROUTES);
            map.remove(id);
            return Mono.empty();
        });
    }
}
