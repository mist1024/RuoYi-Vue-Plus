package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("SyncPatient")
public class ftSyncPatient
{
    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    public void ftGetRemotePatient() {

    }
}
