package com.ruoyi.work.mapper;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.work.domain.TProcess;
import com.ruoyi.work.domain.vo.ProcessVo;
import org.apache.ibatis.annotations.Param;

/**
 * 流程Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-03
 */
public interface ProcessMapper extends BaseMapperPlus<ProcessMapper, TProcess, ProcessVo> {

    boolean updateCommonByBusinessId(@Param("bean") String bean, @Param("processStatus") String processStatus, @Param("businessId") String businessId, @Param("step") Object step);
    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    SysRole selectRoleById(@Param("roleId") Long roleId);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    SysDept selectDeptById(@Param("deptId") Long deptId);
    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUser selectUserById(@Param("userId") Long userId);
}
