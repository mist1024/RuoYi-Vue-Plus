package com.ruoyi.quartz.task;

import com.ruoyi.system.fantang.mapper.FtReportMealVoMapper;
import com.ruoyi.system.fantang.service.impl.FtFoodDemandDaoServiceImpl;
import com.ruoyi.system.fantang.service.impl.FtOrderDaoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Slf4j
@Component("OrderingTask")
public class FtGenerateOrderTask {

    @Autowired
    private FtFoodDemandDaoServiceImpl foodDemandDaoService;

    @Autowired
    private FtReportMealVoMapper reportMealVoMapper;

    @Autowired
    private FtOrderDaoServiceImpl orderDaoService;

    public void GenerateOrderTask() {
        System.out.println("执行生成一条病患默认订餐配置记录");
        log.info("执行生成一条病患默认订餐配置记录");
        foodDemandDaoService.GenerateOrderForNewPatient();
    }

    public void GeneratePatientTomorrowReportMeal() {
        System.out.println("生成次日病患报餐记录");
        log.info("生成次日病患报餐记录");
        reportMealVoMapper.insertTomorrowReportMeal();
    }

    // 生成次日员工订餐记录
    public void GenerateStaffTomorrowOrder() {
        log.info("生成次日员工订餐记录");
        orderDaoService.GenerateStaffTomorrowOrder();
    }

    // 更新用餐状态为用餐状态，用餐前提前2小时关闭报餐数据修改
    public void updateBreakfastDinnerFlag() {
      log.info("更新早餐数:{}", reportMealVoMapper.updateBreakfastDinnerFlag());
    }

    // 更新用餐状态为用餐状态，用餐前提前2小时关闭报餐数据修改
    public void updateLunchDinnerFlag() {
        log.info("更新午餐数:{}", reportMealVoMapper.updateLunchDinnerFlag());
    }

    // 更新用餐状态为用餐状态，用餐前提前2小时关闭报餐数据修改
    public void updateDinnerDinnerFlag() {
        log.info("更新晚餐数:{}", reportMealVoMapper.updateDinnerDinnerFlag());

        log.info("更新加餐数:{}", reportMealVoMapper.updateAdditionDinnerFlag());

    }

}
