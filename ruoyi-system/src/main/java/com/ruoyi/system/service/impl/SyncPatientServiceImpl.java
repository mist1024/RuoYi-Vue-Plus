package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.fantang.domain.FtRemotePatientDao;
import com.ruoyi.system.fantang.domain.FtSyncLogDao;
import com.ruoyi.system.fantang.mapper.FtPatientDaoMapper;
import com.ruoyi.system.fantang.mapper.FtSyncLogDaoMapper;
import com.ruoyi.system.fantang.mapper.FtSyncPatientDaoMapper;
import com.ruoyi.system.service.ISyncPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@DataSource(value = DataSourceType.MASTER)
@Service
public class SyncPatientServiceImpl implements ISyncPatientService {
    @Autowired
    private FtSyncPatientDaoMapper syncPatientDaoMapper;

    @Autowired
    private FtPatientDaoMapper patientDaoMapper;

    @Autowired
    private FtSyncLogDaoMapper syncLogDaoMapper;

    // 从远程数据源插上病患数据
    @Transactional
    @Override
    public Integer insertToLocalSync(List<FtRemotePatientDao> remotePatientDaoList) {
        // 清空本地中间表数据，准备接收同步数据
        syncPatientDaoMapper.delete(null);

        // 遍历数据源，逐条插入本地中间表
        for (FtRemotePatientDao dao : remotePatientDaoList) {
            syncPatientDaoMapper.insert(dao);
        }

        // 初始化本地病患表准备同步，将标志位置“0”
        int ret = patientDaoMapper.initForSync();

        // 更新住院号相同的记录，并标注flag=1
        int syncCount = patientDaoMapper.syncEqualHospitalId();

        // 从中间表添加新增病患数据，并标注flag=2
        ret = patientDaoMapper.syncNewHospitalId();
        System.out.println(ret);

        // 将没有同步的病患数据设置为出院状态, off_flag=1
        patientDaoMapper.updateOffHospitalFlag();

        // 为新病患记录填入对应的科室id
        patientDaoMapper.updateDepartIDToNewPatient();

        // 创建同步日志记录
        FtSyncLogDao syncLogDao = new FtSyncLogDao();
        syncLogDao.setCreateAt(new Date());
        syncLogDao.setTotalRecord((long) remotePatientDaoList.size());
        syncLogDao.setSyncRecord((long) syncCount);
        syncLogDaoMapper.insert(syncLogDao);

        return remotePatientDaoList.size();
    }
}
