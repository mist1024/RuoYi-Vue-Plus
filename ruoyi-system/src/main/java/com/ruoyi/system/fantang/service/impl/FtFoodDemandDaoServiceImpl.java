package com.ruoyi.system.fantang.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.mapper.FtFoodDemandDaoMapper;
import com.ruoyi.system.fantang.service.IFtFoodDemandDaoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 病人报餐Service业务层处理
 *
 * @author ft
 * @date 2020-12-03
 */
@Service
public class FtFoodDemandDaoServiceImpl extends ServiceImpl<FtFoodDemandDaoMapper, FtFoodDemandDao> implements IFtFoodDemandDaoService {

    @Override
    public Integer GenerateOrderByPatientId(Long patientId) {
        return this.baseMapper.GenerateOrderByPatientId(patientId);
    }

    // 扫描所有未设置默认订餐信息的病人，增加配置信息
    @Override
    public Integer GenerateOrderForNewPatient() {
        // 获取所有未设置默认订餐需求病人
        List<Long> newPatients = this.baseMapper.getNewPatientNotDemand();
        for (Long patientId : newPatients) {
            this.baseMapper.GenerateOrderByPatientId(patientId);
        }
        return newPatients.size();
    }

    @Override
    public List<FtFoodDemandDao> listNewFormatter(FtFoodDemandDao ftFoodDemandDao) {
        return this.baseMapper.listNewFormatter(ftFoodDemandDao);
    }

    @Override
    public FtFoodDemandDao getByIdNewFormatter(Long id) {
        return this.baseMapper.getByIdNewFormatter(id);
    }

    @Override
    public List<FtOrderDao> getStatisticsOfDay(Date day) {
        return this.baseMapper.getStatisticsOfDate(DateUtil.beginOfDay(day).toString(), DateUtil.endOfDay(day).toString());
    }

    @Override
    public List<FtOrderDao> getStatisticsOfWeek(Date day) {
        return this.baseMapper.getStatisticsOfDate(DateUtil.beginOfWeek(day).toString(), DateUtil.endOfWeek(day).toString());
    }

    @Override
    public List<FtOrderDao> getStatisticsOfMonth(Date day) {
        return this.baseMapper.getStatisticsOfDate(DateUtil.beginOfMonth(day).toString(), DateUtil.endOfMonth(day).toString());
    }
}
