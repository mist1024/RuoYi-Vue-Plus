package com.ruoyi.isc.utils;

import com.ruoyi.common.constant.IscConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.isc.domain.IscAppService;
import com.ruoyi.isc.utils.beans.IscFilterDefinition;
import com.ruoyi.isc.utils.beans.IscPredicateDefinition;
import com.ruoyi.isc.utils.beans.IscRouteDefinition;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Wenchao Gong
 * @date 2021/9/10 14:46
 */
public class RouteUtils {
    /**
     * Gateway 虚拟路径前缀
     */
    public static final String PATH_PREFIX = "/proxy";
    private static RedissonClient client = SpringUtils.getBean(RedissonClient.class);

    /**
     * 获取虚拟URL Path 部分
     *
     * @param applicationId 应用ID
     * @param serviceId     服务Id
     * @param serviceAddr   服务地址
     * @return 虚拟地址Path
     */
    public static String genVirtualAddrPath(Long applicationId, Long serviceId, String serviceAddr)
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
        try
        {
            return new URI(uri);
        } catch (URISyntaxException e)
        {
            throw new ServiceException("URL格式异常");
        }
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
        final RMap<String, IscRouteDefinition> map = client.getMap(IscConstants.KEY_ROUTES);
        map.clear();
        map.putAll(routeMap);
        return true;
    }

    /**
     * 保存路由信息
     *
     * @param route 路由信息
     * @return 是否成功
     */
    public static boolean saveRoute(IscRouteDefinition route)
    {
        final RMap<String, IscRouteDefinition> map = client.getMap(IscConstants.KEY_ROUTES);
        map.put(route.getId(), route);
        return true;
    }

    /**
     * 删除路由信息
     *
     * @param routeId 路由ID
     * @return 是否成功
     */
    public static boolean deleteRoute(String routeId)
    {
        final RMap<String, IscRouteDefinition> map = client.getMap(IscConstants.KEY_ROUTES);
        map.remove(routeId);
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
        return saveRoute(route);
    }

    /**
     * 生成 路由信息
     *
     * @param appService  应用服务信息
     * @param serviceAddr 服务地址
     * @return 路由信息
     */
    public static IscRouteDefinition generateRoute(IscAppService appService, String serviceAddr)
    {
        IscRouteDefinition route = new IscRouteDefinition();
        route.setId(String.valueOf(appService.getAppServiceId()));
        route.setUri(getURI(serviceAddr.substring(0, serviceAddr.length() - getPathUri(serviceAddr).length())));

        //断言
        IscPredicateDefinition pathPredicate = new IscPredicateDefinition();
        pathPredicate.setName("Path");
        pathPredicate.getArgs().put("pattern", appService.getVirtualAddr());
        route.setPredicates(Arrays.asList(pathPredicate));

        //过滤器
        IscFilterDefinition stripPrefixFilter = new IscFilterDefinition();
        stripPrefixFilter.setName("StripPrefix");
        stripPrefixFilter.getArgs().put("parts", "1");

        IscFilterDefinition retryFilter = new IscFilterDefinition();
        retryFilter.setName("Retry");
        retryFilter.getArgs().put("retries", "1");
        route.setFilters(Arrays.asList(stripPrefixFilter, retryFilter));

        //其他信息
        Map<String, Object> metadata = route.getMetadata();
        metadata.put("secondsLimit", toString(appService.getQuotaSeconds()));
        metadata.put("minutesLimit", toString(appService.getQuotaMinutes()));
        metadata.put("hoursLimit", toString(appService.getQuotaHours()));
        metadata.put("daysLimit", toString(appService.getQuotaDays()));
        metadata.put("expire", toString(appService.getEndTime()));
        return route;
    }

    /**
     * 对象转字符串
     *
     * @param obj 具体属性
     * @return
     */
    public static String toString(Object obj)
    {
        return Objects.isNull(obj) ? StringUtils.EMPTY : obj instanceof Date ?
                DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, (Date) obj) : String.valueOf(obj);
    }
}
