package com.ruoyi.work.service;

import com.ruoyi.common.core.domain.entity.SysUser;
import java.util.List;

/**
 * 用户 业务层
 *
 * @author Lion Li
 */
public interface IWorkSysUserService {



    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectUserList(SysUser user);



    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser selectUserByUserName(String userName);

    /**
     * 通过手机号查询用户
     *
     * @param phonenumber 手机号
     * @return 用户对象信息
     */
    SysUser selectUserByPhonenumber(String phonenumber);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUser selectUserById(Long userId);



}
