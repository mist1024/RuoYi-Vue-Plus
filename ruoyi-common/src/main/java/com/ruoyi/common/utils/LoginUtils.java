package com.ruoyi.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.ruoyi.common.enums.DeviceType;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.exception.UtilException;

/**
 * 登录鉴权工具
 * 为适配多端登录而封装
 *
 * @author Lion Li
 */
public class LoginUtils {

    /**
     * 登录系统
     * 针对两套用户体系
     * @param userId 用户id
     */
    public static void login(Long userId, UserType userType) {
        StpUtil.login(userType.getUserType() + userId);
    }

    /**
     * 登录系统 基于 设备类型
     * 针对一套用户体系
     * @param userId 用户id
     */
    public static void loginByDevice(Long userId, UserType userType, String deviceType) {
        StpUtil.login(userType.getUserType() + userId, deviceType);
    }

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        return parseUserId(StpUtil.getLoginIdAsString());
    }

    /**
     * 获取用户类型
     */
    public static UserType getUserType() {
        String loginId = StpUtil.getLoginIdAsString();
        return getUserType(loginId);
    }

    /**
     * 根据登录ID获取用户类型
	 *
     * @param loginId 登录ID
     * @return 用户类型
     */
    public static UserType getUserType(Object loginId) {
        for (UserType userType : UserType.values()) {
            if (StringUtils.startsWith(loginId.toString(), userType.getUserType())) {
                return userType;
            }
        }
        throw new UtilException("登录用户: LoginId异常 => " + loginId);
    }

    /**
     * 获取用户ID
     */
    public static Long parseUserId(Object loginId) {
        UserType userType = getUserType(loginId);
        return Long.parseLong(StringUtils.replace(loginId.toString(), userType.getUserType(), StringUtils.EMPTY));
    }
}
