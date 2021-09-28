package com.ruoyi.demo.app.service;

import com.ruoyi.demo.app.domain.AppUser;
import org.springframework.stereotype.Service;

/*
* 接口
* */
public interface IAppUserService {

    AppUser queryById(Long id);
}
