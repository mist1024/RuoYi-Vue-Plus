package com.ruoyi.system.fantang.service;

import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 病人报餐Service接口
 *
 * @author ft
 * @date 2020-12-03
 */
public interface IFtFoodDemandDaoService extends IService<FtFoodDemandDao> {
    public Integer GenerateOrderByPatientId(Long patientId);

    public Integer GenerateOrderForNewPatient() ;

}
