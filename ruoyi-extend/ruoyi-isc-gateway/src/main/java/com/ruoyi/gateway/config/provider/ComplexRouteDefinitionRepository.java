package com.ruoyi.gateway.config.provider;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * 复合的路由存储器
 *
 * @author Wenchao Gong
 * @date 2021-09-11
 */
public class ComplexRouteDefinitionRepository implements RouteDefinitionRepository {

    private RouteDefinitionRepository localCache;
    private RouteDefinitionRepository remoteReps;

    public ComplexRouteDefinitionRepository(RouteDefinitionRepository localCache, RouteDefinitionRepository remoteReps)
    {
        this.localCache = localCache;
        this.remoteReps = remoteReps;
    }


    @Override
    public Flux<RouteDefinition> getRouteDefinitions()
    {
        final Flux<RouteDefinition> routeDefinitions = localCache.getRouteDefinitions();
        return routeDefinitions.collectList().flatMapIterable(list -> {
            if(list.isEmpty()) {
                final Flux<RouteDefinition> definitions = remoteReps.getRouteDefinitions();
                definitions.collectList().flatMapIterable(routes -> {
                    if(!routes.isEmpty()) {
                        routes.stream().map(m -> {
                            routes.add(m);
                            return Mono.just(m);
                        }).forEach(m -> {
                            localCache.save(m);
                        });
                    }
                    return routes;
                }).filter(l -> Objects.nonNull(l)).map(m -> list.add(m)).collectList();
            }
            return list;
        });
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route)
    {
        remoteReps.save(route);
        return localCache.save(route);
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId)
    {
        remoteReps.delete(routeId);
        return localCache.delete(routeId);
    }
}
