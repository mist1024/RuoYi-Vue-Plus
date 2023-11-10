package com.go.view.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.go.view.admin.domain.GoviewProjectData;

public interface IGoviewProjectDataService extends IService<GoviewProjectData> {

	public GoviewProjectData getProjectid(String projectId);

}
