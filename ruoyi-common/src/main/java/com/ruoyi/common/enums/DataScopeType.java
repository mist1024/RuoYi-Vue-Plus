package com.ruoyi.common.enums;

import com.ruoyi.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限类型
 *
 * 语法支持 spel 模板表达式
 *
 * 内置数据 当前用户的 deptId 部门id roleId 角色id userId 用户id
 *
 * @author Lion Li
 */
@Getter
@AllArgsConstructor
public enum DataScopeType {

    /**
     * 全部数据权限
     */
    ALL("1", ""),

    /**
     * 自定数据权限
     */
    CUSTOM("2", " #{#deptName} IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = #{#roleId} ) "),

    /**
     * 部门数据权限
     */
    DEPT("3", " #{#deptName} = #{#deptId} "),

    /**
     * 部门及以下数据权限
     */
    DEPT_AND_CHILD("4", " #{#deptName} IN ( SELECT dept_id FROM sys_dept WHERE dept_id = #{#deptId} OR find_in_set( #{#deptId} , ancestors ) )"),

    /**
     * 仅本人数据权限
     */
    SELF("5", " #{#userName?:1} = #{#userId} ");

    private final String code;

    /**
     * 语法 采用 spel 模板表达式
     */
    private final String sql;

    public static DataScopeType findCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (DataScopeType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
