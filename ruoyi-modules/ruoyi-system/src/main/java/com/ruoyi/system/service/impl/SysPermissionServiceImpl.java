package com.ruoyi.system.service.impl;

import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysPermissionService;
import com.ruoyi.system.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    private final ISysRoleService roleService;
    private final ISysMenuService menuService;

    /**
     * 获取角色数据权限
     *
     * @param userId  用户id
     * @param isAdmin 是否管理员
     * @return 角色权限信息
     */
    @Override
    public Set<String> getRolePermission(Long userId, boolean isAdmin) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (isAdmin) {
            roles.add("admin");
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(userId));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param userId  用户id
     * @param isAdmin 是否管理员
     * @return 菜单权限信息
     */
    @Override
    public Set<String> getMenuPermission(Long userId, boolean isAdmin) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (isAdmin) {
            perms.add("*:*:*");
        } else {
            perms.addAll(menuService.selectMenuPermsByUserId(userId));
        }
        return perms;
    }
}
