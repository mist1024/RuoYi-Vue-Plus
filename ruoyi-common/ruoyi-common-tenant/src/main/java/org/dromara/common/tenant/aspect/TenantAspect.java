package org.dromara.common.tenant.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.dromara.common.tenant.annotation.IgnoreTenant;
import org.dromara.common.tenant.helper.TenantHelper;
import org.springframework.core.annotation.Order;

/**
 * 租户切面
 *
 * @author yixiacoco
 */
@Aspect
@Order(-1)
public class TenantAspect {

    /**
     * 处理忽略租户
     *
     * @param point        织入点
     * @param ignoreTenant 忽略注解
     */
    @Around("@within(ignoreTenant) || @annotation(ignoreTenant)")
    public Object handleIgnore(ProceedingJoinPoint point, IgnoreTenant ignoreTenant) throws Throwable {
        TenantHelper.enableIgnore();
        try {
            return point.proceed();
        } finally {
            TenantHelper.disableIgnore();
        }
    }
}
