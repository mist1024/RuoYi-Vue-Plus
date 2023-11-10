package com.go.view.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.go.view.admin.domain.GoviewUser;
import com.go.view.admin.mapper.GoviewUserMapper;
import com.go.view.admin.service.IGoviewUserService;
import org.springframework.stereotype.Service;

@Service
public class GoviewUserServiceImpl extends ServiceImpl<GoviewUserMapper, GoviewUser> implements IGoviewUserService {

}
