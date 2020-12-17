package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.fantang.domain.FtRemotePatientDao;
import com.ruoyi.system.fantang.domain.FtSyncLogDao;
import com.ruoyi.system.fantang.mapper.*;
import com.ruoyi.system.fantang.vo.ftSyncConflictVo;
import com.ruoyi.system.service.ISyncPatientService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FtNotifyDaoMapper notifyDaoMapper;

    @Autowired
    private FtSyncConflictDaoMapper syncConflictDaoMapper;

    /**
     * 从远程数据源插上病患数据
     * 业务逻辑：
     *      1、本地同步表将标志位置 0 等待同步状态；
     *      2、
     *
     * @param remotePatientDaoList
     * @return
     */
    @Transactional
    @Override
    public Integer insertToLocalSync(List<FtRemotePatientDao> remotePatientDaoList) {
        StringBuilder syncMessage = new StringBuilder();
        syncMessage.append("同步信息:");
        // 清空本地中间表数据，准备接收同步数据
        syncPatientDaoMapper.delete(null);

        // 遍历数据源，逐条插入本地中间表
        for (FtRemotePatientDao dao : remotePatientDaoList) {
            syncPatientDaoMapper.insert(dao);
        }

        // 初始化本地病患表准备同步，将标志位置“0”
        int ret = patientDaoMapper.initForSync();
        syncMessage.append(String.format("本地初始化记录：%d 条",ret));


        // 同步逻辑1：更新住院号、科室、床号、姓名全部相同的记录，并标注flag=1
        int syncCount = patientDaoMapper.syncEqual();
        syncMessage.append(String.format("更新逻辑1： %d 条", syncCount));

        // 同步逻辑2：更新住院号、姓名相同的记录，并标注flag=1
        syncCount = patientDaoMapper.syncEqualForHospitalAndName();
        syncMessage.append(String.format("更新逻辑2： %d 条", syncCount));

        // 同步逻辑3：新增住院号、科室、床号相同的记录，作为新病患同步，并标注flag=2
        syncCount = patientDaoMapper.syncNewHospitalId();
        syncMessage.append(String.format("更新逻辑3： %d 条", syncCount));

        // 冲突逻辑1：住院号相同、科室、床号、姓名都不相同的记录，作为冲突数据，并标注flag=3
        List<ftSyncConflictVo> syncPatientVos = patientDaoMapper.syncConflictOnlyHospitalEqual();
        syncMessage.append(String.format("冲突逻辑1： %d 条", syncPatientVos.size()));
        for (ftSyncConflictVo vo : syncPatientVos) {
            syncConflictDaoMapper.insert(vo);
        }

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
