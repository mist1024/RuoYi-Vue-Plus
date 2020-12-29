package com.ruoyi.system.fantang.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.vo.FtPatientVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.mapper.FtPatientDaoMapper;
import com.ruoyi.system.fantang.domain.FtPatientDao;
import com.ruoyi.system.fantang.service.IFtPatientDaoService;

import java.util.List;

/**
 * 病人管理Service业务层处理
 *
 * @author ft
 * @date 2020-11-24
 */
@Service
public class FtPatientDaoServiceImpl extends ServiceImpl<FtPatientDaoMapper, FtPatientDao> implements IFtPatientDaoService {

    @Override
    public AjaxResult getReportMealsToday(String createAt, Long patientId) {
        return AjaxResult.success(this.baseMapper.getReportMealsToday(createAt, patientId));
    }
}
