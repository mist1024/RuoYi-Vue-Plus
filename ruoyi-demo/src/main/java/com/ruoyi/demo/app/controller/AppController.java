package com.ruoyi.demo.app.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.utils.LoginUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.demo.app.domain.AppUser;
import com.ruoyi.demo.app.domain.vo.AppUserVo;
import com.ruoyi.demo.app.utils.AppSecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *@DESCRIPTION
 *@author ye
 *@create 2021/9/28 19:10
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("app")
public class AppController extends BaseController {

    @GetMapping("appLogin")
    public AjaxResult appLogin(String code) {
        UserType userType = LoginUtils.getUserType();
        if (userType == UserType.SYS_USER) {
            return error("系统用户不能登录");
        } else if (userType == UserType.APP_USER) {
            return AjaxResult.success("请求头有用户信息", AppSecurityUtils.getUser());
        }
        //说明没有token或者token为空 这时候需要根据code来获取openid存到数据库
        String openid = "code" + code;//代表获取了openid
        //保存一条数据
        AppUserVo appUser = new AppUserVo();
        appUser.setId(123L);//设置主键id
        appUser.setOpenid(openid);//设置唯一用户标识
        appUser.setName("app" + openid);//设置一个昵称
        LoginUtils.login(appUser.getId(), UserType.APP_USER);//登录
        String token = LoginUtils.getToken();//获取token
        appUser.setToken(token);//将token传回给前端

        return AjaxResult.success("新用户注册成功",appUser);
    }

}
