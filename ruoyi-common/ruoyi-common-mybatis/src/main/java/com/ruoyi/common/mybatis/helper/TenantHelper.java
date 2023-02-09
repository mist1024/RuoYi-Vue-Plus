package com.ruoyi.common.mybatis.helper;

import com.baomidou.mybatisplus.core.plugins.IgnoreStrategy;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.ruoyi.common.core.utils.SpringUtils;
import com.ruoyi.common.mybatis.properties.TenantProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 租户助手
 *
 * @author Lion Li
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TenantHelper {

    private static final TenantProperties PROPERTIES = SpringUtils.getBean(TenantProperties.class);

    /**
     * 租户功能是否启用
     */
    public static boolean isEnable() {
        return PROPERTIES.getEnable();
    }

    /**
     * 开启忽略租户(开启后需手动调用 {@link #disableIgnore()} 关闭)
     */
    public static void enableIgnore() {
        InterceptorIgnoreHelper.handle(IgnoreStrategy.builder().tenantLine(true).build());
    }

    /**
     * 关闭忽略租户
     */
    public static void disableIgnore() {
        InterceptorIgnoreHelper.clearIgnoreStrategy();
    }
}
