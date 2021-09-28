package com.ruoyi.demo.app.service.impl;

import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.demo.app.domain.AppUser;
import com.ruoyi.demo.app.domain.vo.AppUserVo;
import com.ruoyi.demo.app.mapper.AppUserMapper;
import com.ruoyi.demo.app.service.IAppUserService;
import org.springframework.stereotype.Service;

/*
 *@DESCRIPTION
 *@author ye
 *@create 2021/9/28 19:20
 */
@Service
public class AppUserServiceImpl  extends ServicePlusImpl<AppUserMapper, AppUser, AppUserVo> implements IAppUserService {

    /*模拟查询用户*/
    @Override
    public AppUser queryById(Long id) {
        AppUser appUser = new AppUser();
        appUser.setId(id);
        appUser.setName("app" + id);
        return appUser;
    }

}
