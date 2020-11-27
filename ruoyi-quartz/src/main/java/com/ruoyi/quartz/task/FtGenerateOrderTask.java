package com.ruoyi.quartz.task;

import com.ruoyi.system.fantang.service.impl.FtFoodDemandDaoServiceImpl;
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
    public void GenerateOrderTask() {
        System.out.println("执行生成一条病患默认订餐配置记录");
        foodDemandDaoService.GenerateOrderForNewPatient();
    }
}
