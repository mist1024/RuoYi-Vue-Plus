package com.ruoyi.isc.common.utils;

/**
 * @author Wenchao Gong
 * @date 2021-10-30
 */
public class GatewayUtils {

    /**
     * 获取 路由Key
     *
     * @param ak        AccessKey
     * @param serviceId 服务ID
     * @return 路由Key
     */
    public static String getRouteKey(String ak, String serviceId) {
        return ak + ':' + serviceId;
    }

    /**
     * 获取 路由Key
     *
     * @param ak        AccessKey
     * @param serviceId 服务ID
     * @return 路由Key
     */
    public static String getRouteKey(String ak, Long serviceId) {
        return getRouteKey(ak, String.valueOf(serviceId));
    }
}
