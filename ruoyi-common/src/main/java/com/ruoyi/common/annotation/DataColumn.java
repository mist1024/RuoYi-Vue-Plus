package com.ruoyi.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限
 *
 * @author Lion Li
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataColumn {

    String key();

    String value();

}
