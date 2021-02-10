package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.fantang.domain.FtCateringDao;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;

import java.util.Date;
import java.util.List;

/**
 * 病人报餐Service接口
 *
 * @author ft
 * @date 2020-12-03
 */
public interface IFtFoodDemandDaoService extends IService<FtFoodDemandDao> {
    public Integer GenerateOrderByPatientId(Long patientId);

    public Integer GenerateOrderForNewPatient();

    List<FtFoodDemandDao> listNewFormatter(FtFoodDemandDao ftFoodDemandDao);

    FtFoodDemandDao getByIdNewFormatter(Long id);

    List<FtOrderDao> getStatisticsOfDay(Date day);

    List<FtOrderDao> getStatisticsOfWeek(Date day);

    List<FtOrderDao> getStatisticsOfMonth(Date day);

    List<FtReportMealsDao> getStatisticsFoodDemand();

    Integer updateDayFoodDemand(List<FtCateringDao> ftCateringList);

    Integer cancelNutritionByPatientId(Long[] ids);
}
