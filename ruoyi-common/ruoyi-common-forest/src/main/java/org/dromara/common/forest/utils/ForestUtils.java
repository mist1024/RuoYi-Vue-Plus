package org.dromara.common.forest.utils;

import org.dromara.common.core.utils.StringUtils;

/**
 * Forest第三方请求工具类
 *
 * @author AprilWind
 */
public class ForestUtils {

    /**
     * 生成参数片段
     *
     * @param name  参数名
     * @param value 参数值
     * @return 带参数的URL片段，如果参数值为空，则返回空字符串
     */
    public static String parameter(String name, String value) {
        return StringUtils.isNotBlank(value) ? "&" + name + "=" + value : "";
    }

    /**
     * 生成参数片段
     *
     * @param name  参数名称
     * @param value 参数值
     * @return 带参数的URL片段，如果参数值为空，则返回空字符串
     */
    public static String parameter(String name, Integer value) {
        return value != null ? "&" + name + "=" + value : "";
    }

}
