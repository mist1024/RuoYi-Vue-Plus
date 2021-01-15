package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.entity.ReportMealsDayEntity;
import com.ruoyi.system.fantang.entity.ReportMealsPriceEntity;
import com.ruoyi.system.fantang.vo.FtReportMealVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 报餐管理Service接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface IFtReportMealsDaoService extends IService<FtReportMealsDao> {

    List<FtReportMealVo> listAll();

    List<FtReportMealVo> listNoPay();

    List<FtReportMealVo> listPayoff();

    Long countBillingBetween(ReportMealsDayEntity dao);

    List<FtReportMealsDao> listMealsWithInSettle(FtReportMealsDao ftReportMealsDao);

    Integer settleMeals(Long settlementId, Long patientId, String lastBillingDate, String selectBillingDate);

    ReportMealsPriceEntity sumTotalPrice(Long patientId, Date lastBillingDate, Date selectBillingDate);

    FtReportMealsDao getLastReportMeals(Long patientId);
}

