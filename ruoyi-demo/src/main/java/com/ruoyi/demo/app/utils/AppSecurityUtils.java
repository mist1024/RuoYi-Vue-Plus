package com.ruoyi.demo.app.utils;

import cn.hutool.http.HttpStatus;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.service.IUserService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.LoginUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.demo.app.domain.AppUser;
import com.ruoyi.demo.app.service.IAppUserService;

/*
 *@DESCRIPTION
 *@author ye
 *@create 2021/9/28 19:55
 */
public class AppSecurityUtils {

    /**
     * 用户ID
     **/
    public static Long getUserId() {
        return LoginUtils.getUserId();
    }

    /**
     * 获取用户
     **/
    public static AppUser getUser() {
        try {
            return SpringUtils.getBean(IAppUserService.class).queryById(getUserId());
        } catch (Exception e) {
            throw new ServiceException("获取用户信息异常", HttpStatus.HTTP_UNAUTHORIZED);
        }
    }

}
