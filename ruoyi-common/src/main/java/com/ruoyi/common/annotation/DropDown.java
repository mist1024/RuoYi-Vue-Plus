package com.ruoyi.common.annotation;

import java.lang.annotation.*;

/**
 * Excel下拉注解
 * <p>注解在实体类中的属性上，与@ExcelProperty同级</p>
 *
 * @author slYe
 * @author Emil.Zhang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DropDown {
    /**
     * 下拉框可选值，用于简单的指定可选值
     * <p>例如： @DropDown({"选项1","选项2"})</p>
     */
    String[] value();
}
