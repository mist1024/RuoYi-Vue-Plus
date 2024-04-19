package org.dromara.common.shortlink.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 有效期
 *
 * @author AprilWind
 */
@Getter
@AllArgsConstructor
public enum ValidityType {

    /**
     * 三分钟
     */
    THREE_MINUTES(3 * 60L),

    /**
     * 一个小时
     */
    ONE_HOUR(60 * 60L),

    /**
     * 表示一天（24小时）
     */
    ONE_DAY(24 * 60 * 60L),

    /**
     * 表示三天
     */
    THREE_DAYS(3 * 24 * 60 * 60L),

    /**
     * 表示七天（一周）
     */
    SEVEN_DAYS(7 * 24 * 60 * 60L),

    /**
     * 表示三十天（一个月）
     */
    THIRTY_DAYS(30 * 24 * 60 * 60L);

    /**
     * 有效期（单位：秒）
     */
    private final Long validityType;

}
