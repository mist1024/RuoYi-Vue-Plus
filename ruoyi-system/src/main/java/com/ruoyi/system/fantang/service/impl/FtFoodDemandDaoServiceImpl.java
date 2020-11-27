package com.ruoyi.system.fantang.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.mapper.FtFoodDemandDaoMapper;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.service.IFtFoodDemandDaoService;

import java.util.List;

/**
 * 病人报餐Service业务层处理
 *
 * @author ft
 * @date 2020-11-26
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
}
