package com.go.view.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.go.view.admin.domain.GoviewProject;
import com.go.view.admin.mapper.GoviewProjectMapper;
import com.go.view.admin.service.IGoviewProjectService;
import org.springframework.stereotype.Service;

@Service
public class GoviewProjectServiceImpl extends ServiceImpl<GoviewProjectMapper, GoviewProject> implements IGoviewProjectService {

}
