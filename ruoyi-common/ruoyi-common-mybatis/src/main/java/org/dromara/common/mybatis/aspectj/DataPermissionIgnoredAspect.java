package org.dromara.common.mybatis.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.dromara.common.mybatis.annotation.DataPermissionIgnored;
import org.dromara.common.mybatis.helper.DataPermissionHelper;

/**
 * 忽略数据权限, 相应注解 {@link DataPermissionIgnored }
 *
 * @author dhb52
 * @since 5.2.1
 */
@Aspect
public class DataPermissionIgnoredAspect {

    @Around("@annotation(dataPermissionIgnored)")
    @SuppressWarnings("unused")
    public Object around(ProceedingJoinPoint joinPoint, DataPermissionIgnored dataPermissionIgnored) {

        return DataPermissionHelper.ignore(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

    }
}
