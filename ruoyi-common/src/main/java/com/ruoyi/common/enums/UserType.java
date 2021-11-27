package com.ruoyi.common.enums;

import com.ruoyi.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户类型
 * 针对两套 用户体系
 *
 * @author Lion Li
 */
@Getter
@AllArgsConstructor
public enum UserType {

    /**
     * pc端
     */
    SYS_USER("sys_user:", DeviceType.PC),

    /**
     * app端
     */
    APP_USER("app_user:", DeviceType.APP);

    /**
     * 用户类型
     */
    private final String userType;

    /**
     * 设备类型
     */
    private final DeviceType deviceType;

    public static UserType getByDeviceType(String deviceType) {
        return Arrays.stream(UserType.values())
                .filter(type -> type.getDeviceType().getDevice().equalsIgnoreCase(deviceType))
                .findFirst()
                .orElseThrow(() -> new ServiceException("设备类型不支持:" + deviceType));
    }
}
