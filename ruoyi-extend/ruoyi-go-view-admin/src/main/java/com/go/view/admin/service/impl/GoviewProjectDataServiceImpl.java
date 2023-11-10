package com.go.view.admin.service.impl;

import com.go.view.admin.mapper.GoviewProjectDataMapper;
import com.go.view.admin.domain.GoviewProjectData;
import com.go.view.admin.service.IGoviewProjectDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GoviewProjectDataServiceImpl extends ServiceImpl<GoviewProjectDataMapper, GoviewProjectData> implements IGoviewProjectDataService {
    final GoviewProjectDataMapper dataMapper;
	@Override
	public GoviewProjectData getProjectid(String projectId) {
		LambdaQueryWrapper<GoviewProjectData> lambdaQueryWrapper=new LambdaQueryWrapper<GoviewProjectData>();
		lambdaQueryWrapper.eq(GoviewProjectData::getProjectId, projectId);
		return dataMapper.selectOne(lambdaQueryWrapper);

	}

}
