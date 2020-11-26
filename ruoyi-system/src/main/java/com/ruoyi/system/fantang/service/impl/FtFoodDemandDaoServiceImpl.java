package com.ruoyi.system.fantang.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.mapper.FtFoodDemandDaoMapper;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.service.IFtFoodDemandDaoService;

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
}
