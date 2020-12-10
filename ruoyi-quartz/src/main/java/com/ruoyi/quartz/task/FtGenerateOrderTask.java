package com.ruoyi.quartz.task;

import com.ruoyi.system.fantang.mapper.FtReportMealVoMapper;
import com.ruoyi.system.fantang.service.impl.FtFoodDemandDaoServiceImpl;
import com.ruoyi.system.fantang.service.impl.FtOrderDaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("OrderingTask")
public class FtGenerateOrderTask {

    @Autowired
    private FtFoodDemandDaoServiceImpl foodDemandDaoService;

    @Autowired
    private FtReportMealVoMapper ftReportMealVoMapper;

    @Autowired
    private FtOrderDaoServiceImpl orderDaoService;

    public void GenerateOrderTask() {
        System.out.println("执行生成一条病患默认订餐配置记录");
        foodDemandDaoService.GenerateOrderForNewPatient();
    }

    public void GeneratePatientTomorrowReportMeal() {
        System.out.println("生成次日病患报餐记录");
        ftReportMealVoMapper.insertTomorrowReportMeal();
    }

    // 生成次日员工订餐记录
    public void GenerateStaffTomorrowOrder() {
        System.out.println("生成次日员工订餐记录");
        orderDaoService.GenerateStaffTomorrowOrder();
    }
}
