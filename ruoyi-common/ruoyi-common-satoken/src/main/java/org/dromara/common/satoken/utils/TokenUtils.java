package org.dromara.common.satoken.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.common.core.enums.DeviceType;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * token工具类
 *
 * @author 21001
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {

    /**
     * 会话id（loginId）前缀
     */
    public static final String SYS_USER = "sys_user:";

    /**
     * 获取当前登录用户的token
     */
    public static String getTokenId() {
        return StpUtil.getTokenInfo().getTokenValue();
    }

    /**
     * 获取当前用户的 token 集合
     */
    public static List<String> getTokenIds() {
        return StpUtil.getTokenValueListByLoginId(SYS_USER + LoginHelper.getUserId());
    }

    /**
     * 获取当前用户 指定设备类型端的 token 集合
     */
    public static List<String> getTokenIds(DeviceType device) {
        return StpUtil.getTokenValueListByLoginId(SYS_USER + LoginHelper.getUserId(), device.getDevice());
    }

    /**
     * 获取指定账号 userId 的 token 集合
     */
    public static List<String> getTokenIds(Long userId) {
        return StpUtil.getTokenValueListByLoginId(SYS_USER + userId);
    }

    /**
     * 获取账号 userId 指定设备类型端的 token 集合
     */
    public static List<String> getTokenIds(Long userId, DeviceType device) {
        return StpUtil.getTokenValueListByLoginId(SYS_USER + userId, device.getDevice());
    }

    /**
     * 获取指定账号 userIds 的 token 集合
     */
    public static List<String> getTokenUserIds(Set<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return userIds.stream()
            .flatMap(userId -> StpUtil.getTokenValueListByLoginId(SYS_USER + userId)
                .stream())
            .collect(Collectors.toList());
    }

    /**
     * 获取账号 userIds 指定设备类型端的 token 集合
     */
    public static List<String> getTokenUserIds(Set<Long> userIds, DeviceType device) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return userIds.stream()
            .flatMap(userId -> StpUtil.getTokenValueListByLoginId(SYS_USER + userId, device.getDevice())
                .stream())
            .collect(Collectors.toList());
    }

}
