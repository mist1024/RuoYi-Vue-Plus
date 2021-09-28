package com.ruoyi.isc.utils;

import cn.hutool.crypto.SecureUtil;
import com.ruoyi.common.exception.ServiceException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Wenchao Gong
 * @date 2021/9/10 14:46
 */
public class RouteUtils
{
    public static final String PATH_PREFIX = "/proxy";

    /**
     * 获取虚拟URL Path 部分
     *
     * @param applicationId 应用ID
     * @param serviceId     服务Id
     * @param serviceAddr   服务地址
     * @return
     */
    public static String genVirtualAddrPath(Long applicationId, Long serviceId, String serviceAddr)
    {
        URI uri = null;
        try {
            uri = new URI(serviceAddr);
            return PATH_PREFIX + uri.getPath();
        } catch (URISyntaxException e) {
            throw new ServiceException("URL格式异常");
        }
    }

}
