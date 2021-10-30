package com.ruoyi.gateway.config.provider;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.ruoyi.isc.common.constant.IscRedisKeys.KEY_ROUTES;

/**
 * Redis 路由仓库
 *
 * @author Wenchao Gong
 * @date 2021-09-11
 */
@Slf4j
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository
{
    public static final Codec ROUTE_CODES_INSTANCE = new TypedJsonJacksonCodec(String.class, RouteDefinition.class);
    private final RedissonClient redissonClient;

    public RedisRouteDefinitionRepository(RedissonClient redissonClient)
    {
        this.redissonClient = redissonClient;
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions()
    {
        RMap<String, RouteDefinition> map = redissonClient.getMap(KEY_ROUTES, ROUTE_CODES_INSTANCE);
        log.debug("Routes from redis size:[{}]!", map.size());
        return Flux.fromIterable(map.values());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route)
    {
        return route.flatMap(definition -> {
            RMap<String, RouteDefinition> map = redissonClient.getMap(KEY_ROUTES, ROUTE_CODES_INSTANCE);
            map.put(definition.getId(), definition);
            log.debug("Saving route to redis id:[{}]!", definition.getId());
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId)
    {
        return routeId.flatMap(id -> {
            RMap<String, RouteDefinition> map = redissonClient.getMap(KEY_ROUTES, ROUTE_CODES_INSTANCE);
            map.remove(id);
            log.debug("Remove route from redis id:[{}]!", id);
            return Mono.empty();
        });
    }
}
