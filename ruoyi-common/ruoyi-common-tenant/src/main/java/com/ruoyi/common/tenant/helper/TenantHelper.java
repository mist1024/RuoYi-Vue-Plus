package com.ruoyi.common.tenant.helper;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.plugins.IgnoreStrategy;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.ruoyi.common.core.constant.TenantConstants;
import com.ruoyi.common.core.utils.SpringUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.tenant.properties.TenantProperties;
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

    private static final String DYNAMIC_TENANT_KEY = TenantConstants.GLOBAL_REDIS_KEY + "dynamicTenant";

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

    /**
     * 设置动态租户(一直有效)
     */
    public static void setDynamic(String tenantId) {
        StpUtil.getTokenSession().set(DYNAMIC_TENANT_KEY, tenantId);
    }

    /**
     * 获取动态租户(一直有效)
     */
    public static String getDynamic() {
        String tenantId = (String) SaHolder.getStorage().get(DYNAMIC_TENANT_KEY);
        if (StringUtils.isNotBlank(tenantId)) {
            return tenantId;
        }
        tenantId = (String) StpUtil.getTokenSession().get(DYNAMIC_TENANT_KEY);
        SaHolder.getStorage().set(DYNAMIC_TENANT_KEY, tenantId);
        return tenantId;
    }

    /**
     * 清除动态租户(一直有效)
     */
    public static void clearDynamic() {
        SaHolder.getStorage().delete(DYNAMIC_TENANT_KEY);
        StpUtil.getTokenSession().delete(DYNAMIC_TENANT_KEY);
    }

}
