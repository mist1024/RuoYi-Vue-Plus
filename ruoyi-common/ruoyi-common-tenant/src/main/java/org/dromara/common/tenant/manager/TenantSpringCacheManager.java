package org.dromara.common.tenant.manager;

import cn.hutool.core.text.StrPool;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.redis.manager.AbstractPlusSpringCacheManager;
import org.dromara.common.tenant.helper.TenantHelper;
import org.springframework.cache.Cache;

/**
 * 重写 cacheName 处理方法 支持多租户
 * 重写 CaffeineCacheDecorator 获取方法 支持多租户
 *
 * @author Supreme
 * @since 2024/07/25
 */
public class TenantSpringCacheManager extends AbstractPlusSpringCacheManager {

    public TenantSpringCacheManager() {
    }


    @Override
    public String getCacheNameWrapper(String cacheName) {
        if (StringUtils.contains(cacheName, GlobalConstants.GLOBAL_REDIS_KEY)) {
            return cacheName;
        }
        String tenantId = TenantHelper.getTenantId();
        if (StringUtils.startsWith(cacheName, tenantId)) {
            // 如果存在则直接返回
            return cacheName;
        }
        return tenantId + StrPool.COLON + cacheName;
    }

    @Override
    public Cache getCaffeineCacheDecorator(Cache cache) {
        return new TenantCaffeineCacheDecorator(cache);
    }
}
