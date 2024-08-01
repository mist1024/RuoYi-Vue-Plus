package org.dromara.common.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 忽略数据权限注解。
 * 当需要在忽略数据权限的上下文中直接或间接调用 Mapper 时候，
 * 可以使用该注解修饰当前函数，避免显式的使用
 * <pre>
 *      DataPermissionHelper.ignore(() -> {
 *          // 业务代码
 *      });
 * </pre>
 * 减少代码作用域层级，这尤其在执行定时任务的时候具有明显优势。
 *
 * @author dhb52
 * @version 5.2.1
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermissionIgnored {
}
