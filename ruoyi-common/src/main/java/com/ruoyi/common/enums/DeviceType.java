package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备类型
 * 针对一套 用户体系
 *
 * @author Lion Li
 */
@Getter
@AllArgsConstructor
public enum DeviceType {

    /**
     * pc端
     */
    PC("pc"),

    /**
     * app端
     */
    APP("app"),

    /**
     * "移动端"
     */
    H5("h5"),

    /**
     * 小程序端
     */
    XCX("xcx"),

    /**
     * "未知"
     */
    UNKNOWN("unknown");

    private final String device;
}
