package com.go.view.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.go.view.admin.domain.GoviewFile;

public interface IGoviewFileService extends IService<GoviewFile> {


	public GoviewFile selectByExamplefileName(String filename);
}
