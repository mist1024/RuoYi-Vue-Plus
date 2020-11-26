package com.ruoyi.quartz.task;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.fantang.mapper.FtFoodDaoMapper;
import com.ruoyi.system.fantang.mapper.FtRemotePatientDaoMapper;
import com.ruoyi.system.service.impl.SyncPatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@DataSource(value = DataSourceType.SLAVE)
@Component("SyncPatient")
public class ftSyncPatient {
    @Autowired
    private FtRemotePatientDaoMapper ftRemotePatientDaoMapper;

    @Autowired
    private SyncPatientServiceImpl syncPatientService;

    public void ftGetRemotePatient() {
        System.out.println("执行同步远程病患数据");
        syncPatientService.insertToLocalSync(ftRemotePatientDaoMapper.selectList(null));
    }
}
