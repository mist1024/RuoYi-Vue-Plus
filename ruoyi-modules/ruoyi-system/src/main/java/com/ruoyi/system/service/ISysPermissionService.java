package com.ruoyi.system.service;

import java.util.Set;

/**
 * 用户权限处理
 *
 * @author Lion Li
 */
public interface ISysPermissionService {

    /**
     * 获取角色数据权限
     *
     * @param userId  用户id
     * @param isAdmin 是否管理员
     * @return 角色权限信息
     */
    Set<String> getRolePermission(Long userId, boolean isAdmin);

    /**
     * 获取菜单数据权限
     *
     * @param userId  用户id
     * @param isAdmin 是否管理员
     * @return 菜单权限信息
     */
    Set<String> getMenuPermission(Long userId, boolean isAdmin);

}
