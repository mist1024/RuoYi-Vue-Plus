package com.go.view.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.go.view.admin.domain.GoviewFile;
import com.go.view.admin.mapper.GoviewFileMapper;
import com.go.view.admin.service.IGoviewFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoviewFileServiceImpl extends ServiceImpl<GoviewFileMapper, GoviewFile> implements IGoviewFileService {
	final GoviewFileMapper sysFileMapper;

	@Override
	public GoviewFile selectByExamplefileName(String filename) {
		GoviewFile sysFile=sysFileMapper.selectOne(new LambdaQueryWrapper<GoviewFile>().eq(GoviewFile::getFileName, filename));
        return sysFile;
	}

}
