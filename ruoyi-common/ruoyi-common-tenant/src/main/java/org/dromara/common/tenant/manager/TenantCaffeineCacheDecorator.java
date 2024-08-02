package org.dromara.common.tenant.manager;

import cn.hutool.core.text.StrPool;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.redis.manager.CaffeineCacheDecorator;
import org.dromara.common.tenant.helper.TenantHelper;
import org.springframework.cache.Cache;

/**
 * 重写 CaffeineCacheDecorator Name处理方法 支持多租户
 *
 * @author Supreme
 * @since 2024/07/25
 */
public class TenantCaffeineCacheDecorator extends CaffeineCacheDecorator {
    public TenantCaffeineCacheDecorator(Cache cache) {
        super(cache);
    }

    @Override
    public String getName() {
        String cacheName = super.getName();
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
}
