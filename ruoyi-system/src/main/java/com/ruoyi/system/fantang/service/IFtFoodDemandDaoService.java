package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 病人报餐Service接口
 *
 * @author ft
 * @date 2020-12-03
 */
public interface IFtFoodDemandDaoService extends IService<FtFoodDemandDao> {
    public Integer GenerateOrderByPatientId(Long patientId);

    public Integer GenerateOrderForNewPatient() ;

    List<FtFoodDemandDao> listNewFormatter(FtFoodDemandDao ftFoodDemandDao);
}
