package com.ruoyi.isc.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.constant.IscConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.isc.domain.IscAppService;
import com.ruoyi.isc.domain.IscService;
import com.ruoyi.isc.utils.beans.IscFilterDefinition;
import com.ruoyi.isc.utils.beans.IscPredicateDefinition;
import com.ruoyi.isc.utils.beans.IscRouteDefinition;
import com.ruoyi.isc.utils.beans.IscRule;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.TypedJsonJacksonCodec;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Wenchao Gong
 * @date 2021/9/10 14:46
 */
@Slf4j
public class RouteUtils {

    public static final Codec ROUTE_CODES_INSTANCE = new TypedJsonJacksonCodec(String.class, IscRouteDefinition.class);
    public static final Codec RULE_CODES_INSTANCE = new TypedJsonJacksonCodec(String.class, IscRule.class);
    public static final String TOPIC_GATEWAY_REFRESH_ROUTE = "TOPIC_GATEWAY_REFRESH_ROUTE";
    /**
     * Gateway 虚拟路径前缀
     */
    public static final String PATH_PREFIX = "/proxy";
    public static final String ACCESS_KEY_NAME = "ak";
    private static RedissonClient client = SpringUtils.getBean(RedissonClient.class);

    /**
     * 获取虚拟URL Path 部分
     *
     * @param serviceAddr 服务地址
     * @return 虚拟地址Path
     */
    public static String genVirtualAddrPath(String serviceAddr)
    {
        return PATH_PREFIX + getPathUri(serviceAddr);
    }

    /**
     * 返回URI 的Path部分
     *
     * @param uri URI
     * @return path
     */
    public static String getPathUri(String uri)
    {
        return getURI(uri).getPath();
    }

    /**
     * 通过 URI字符串 生成URI对象
     *
     * @param uri URI字符串
     * @return URI
     */
    public static URI getURI(String uri)
    {
        try {
            return new URI(uri);
        } catch (URISyntaxException e)
        {
            throw new ServiceException("URL格式异常");
        }
    }

    /**
     * 发布路由刷新通知
     * @param consumer
     * @return
     */
    public static void sendRefreshRouteToGateway(Consumer<String> consumer) {
        RedisUtils.publish(TOPIC_GATEWAY_REFRESH_ROUTE, DateUtil.now(), consumer);
    }

    /**
     * 刷新路由信息 先清空再批量新增
     *
     * @param routes 路由信息
     * @return 是否成功
     */
    public static boolean refreshRoute(List<IscRouteDefinition> routes)
    {
        Map<String, IscRouteDefinition> routeMap = routes.stream().collect(Collectors.toMap(IscRouteDefinition::getId,
                Function.identity(), (o1, o2) -> o2));
        final RMap<String, IscRouteDefinition> map = client.getMap(IscConstants.KEY_ROUTES, ROUTE_CODES_INSTANCE);
        map.clear();
        map.putAll(routeMap);
        sendRefreshRouteToGateway((time) -> log.info("路由刷新完成，通知网关刷新路由![{}]", time));
        return true;
    }

    /**
     * 保存路由信息
     *
     * @param routes 路由信息
     * @return 是否成功
     */
    public static boolean saveRoute(Collection<IscRouteDefinition> routes) {
        return saveRoute(routes, null);
    }

    /**
     * 保存路由信息
     *
     * @param routes    路由信息
     * @param consumer 回调操作
     * @return 是否成功
     */
    public static boolean saveRoute(Collection<IscRouteDefinition> routes, Consumer<String> consumer)
    {
        if(CollectionUtil.isEmpty(routes)) {
            return true;
        }
        final RMap<String, IscRouteDefinition> map = client.getMap(IscConstants.KEY_ROUTES, ROUTE_CODES_INSTANCE);
        Map<String, IscRouteDefinition> collect = routes.stream().collect(Collectors.toMap(IscRouteDefinition::getId, Function.identity()));
        map.putAll(collect);
        if(Objects.isNull(consumer)) {
            consumer = (time) -> log.info("保存路由[{}]完成，通知网关刷新路由![{}]", map.keySet(), time);
        }
        sendRefreshRouteToGateway(consumer);
        return true;
    }

    /**
     * 删除路由信息
     *
     * @param routeIds 路由ID
     * @return 是否成功
     */
    public static boolean deleteRoute(Collection<String> routeIds)
    {
        final RMap<String, IscRouteDefinition> map = client.getMap(IscConstants.KEY_ROUTES, ROUTE_CODES_INSTANCE);
        map.fastRemove(routeIds.toArray(new String[0]));
        sendRefreshRouteToGateway((time) -> log.info("删除路由[{}]完成，通知网关刷新路由![{}]", routeIds, time));
        return true;
    }

    /**
     * 更新路由信息
     *
     * @param route 路由信息
     * @return 是否成功
     */
    public static boolean updateRoute(IscRouteDefinition route)
    {
        boolean result = saveRoute(Collections.singletonList(route), (time) ->
                log.info("更新路由[{}]完成，通知网关刷新路由![{}]", route.getId(), time));
        return result;
    }

    /**
     * 生成 路由信息
     *
     * @param service    服务信息
     * @return 路由信息
     */
    public static IscRouteDefinition generateRoute(IscService service)
    {
        IscRouteDefinition route = new IscRouteDefinition();
        route.setId(String.valueOf(service.getServiceId()));
        final String serviceAddr = service.getServiceAddr();
        route.setUri(getURI(serviceAddr.substring(0, serviceAddr.length() - getPathUri(serviceAddr).length())));

        //断言
        IscPredicateDefinition methodPredicate = new IscPredicateDefinition();
        methodPredicate.setName("Method");
        methodPredicate.getArgs().put("methods", service.getRequestMethod());

        IscPredicateDefinition pathPredicate = new IscPredicateDefinition();
        pathPredicate.setName("Path");
        pathPredicate.getArgs().put("pattern", genVirtualAddrPath(service.getServiceAddr()));
        route.setPredicates(Arrays.asList(methodPredicate, pathPredicate));

        //过滤器
        List<IscFilterDefinition> filters = new ArrayList<>();
        IscFilterDefinition stripPrefixFilter = new IscFilterDefinition();
        stripPrefixFilter.setName("StripPrefix");
        stripPrefixFilter.getArgs().put("parts", "1");
        filters.add(stripPrefixFilter);

        IscFilterDefinition retryFilter = new IscFilterDefinition();
        retryFilter.setName("Retry");
        retryFilter.getArgs().put("retries", "1");
        filters.add(retryFilter);
        route.setFilters(filters);

        //其他信息
        Map<String, Object> metadata = route.getMetadata();
        metadata.put("accessKeyName", ACCESS_KEY_NAME);
        metadata.put("addParam", service.getHiddenParams());
        return route;
    }

    /**
     * 生成 服务对应AK调用规则
     *
     * @param appService
     * @param accessKey
     * @return
     */
    public static IscRule generateRule(IscAppService appService, String accessKey)
    {
        IscRule rule = new IscRule();
        rule.setId(accessKey + ':' + appService.getServiceId());
        rule.setExpire(appService.getEndTime());
        rule.setDaysLimit(appService.getQuotaDays());
        rule.setHoursLimit(appService.getQuotaHours());
        rule.setMinutesLimit(appService.getQuotaMinutes());
        rule.setSecondsLimit(appService.getQuotaSeconds());
        return rule;
    }

    /**
     * 刷新规则信息 先清空再批量新增
     *
     * @param rules 规则信息
     * @return 是否成功
     */
    public static boolean refreshRules(List<IscRule> rules)
    {
        Map<String, IscRule> ruleMap = rules.stream().collect(Collectors.toMap(IscRule::getId, Function.identity(), (o1, o2) -> o2));
        final RMap<String, IscRule> map = client.getMap(IscConstants.KEY_RULES, RULE_CODES_INSTANCE);
        map.clear();
        map.putAll(ruleMap);
        return true;
    }

    /**
     * 保存规则信息
     *
     * @param rule 规则信息
     * @return 是否成功
     */
    public static boolean saveRule(IscRule rule)
    {
        final RMap<String, IscRule> map = client.getMap(IscConstants.KEY_RULES, RULE_CODES_INSTANCE);
        map.put(rule.getId(), rule);
        return true;
    }

    /**
     * 删除规则信息
     *
     * @param ruleId 规则ID
     * @return 是否成功
     */
    public static boolean deleteRule(String ruleId)
    {
        final RMap<String, IscRule> map = client.getMap(IscConstants.KEY_RULES, RULE_CODES_INSTANCE);
        map.remove(ruleId);
        return true;
    }

    /**
     * 更新规则信息
     *
     * @param rule 规则信息
     * @return 是否成功
     */
    public static boolean updateRule(IscRule rule)
    {
        return saveRule(rule);
    }
}
