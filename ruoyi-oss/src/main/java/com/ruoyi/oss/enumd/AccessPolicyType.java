package com.ruoyi.oss.enumd;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 桶访问策略配置
 *
 * @author 陈賝
 */
@Getter
@AllArgsConstructor
public enum AccessPolicyType {

    /**
     * private
     */
    PRIVATE("0"),

    /**
     * public
     */
    PUBLIC("1"),

    /**
     * constum
     */
    CONSTUM("2");

    /**
     * 类型
     */
    private final String type;

}
