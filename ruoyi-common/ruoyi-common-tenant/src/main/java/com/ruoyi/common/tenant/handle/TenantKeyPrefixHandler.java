package com.ruoyi.common.tenant.handle;

import com.ruoyi.common.core.constant.TenantConstants;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.handler.KeyPrefixHandler;
import com.ruoyi.common.satoken.utils.LoginHelper;

/**
 * 多租户redis缓存key前缀处理
 *
 * @author Lion Li
 */
public class TenantKeyPrefixHandler extends KeyPrefixHandler {

    public TenantKeyPrefixHandler(String keyPrefix) {
        super(keyPrefix);
    }

    /**
     * 增加前缀
     */
    @Override
    public String map(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (StringUtils.contains(name, TenantConstants.GLOBAL_REDIS_KEY)) {
            return super.map(name);
        }
        return super.map(LoginHelper.getTenantId() + ":" + name);
    }

    /**
     * 去除前缀
     */
    @Override
    public String unmap(String name) {
        String unmap = super.unmap(name);
        if (StringUtils.isBlank(unmap)) {
            return null;
        }
        if (StringUtils.contains(name, TenantConstants.GLOBAL_REDIS_KEY)) {
            return super.unmap(name);
        }
        return unmap.substring((LoginHelper.getTenantId() + ":").length());
    }

}
