package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.fantang.domain.FtNotifyDao;
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
     * 1、本地同步表将标志位置 0 等待同步状态；
     * 2、
     *
     * @param remotePatientDaoList
     * @return
     */
    @Transactional
    @Override
    public Integer insertToLocalSync(List<FtRemotePatientDao> remotePatientDaoList) {
        System.out.println("开始同步..................");
        StringBuilder syncMessage = new StringBuilder();
        syncMessage.append("同步信息:");
        // 清空本地中间表数据，准备接收同步数据
        syncPatientDaoMapper.delete(null);

        // 遍历数据源，逐条插入本地中间表
        for (FtRemotePatientDao dao : remotePatientDaoList) {
            syncPatientDaoMapper.insert(dao);
        }
        // 为新记录填入对应的科室id
        patientDaoMapper.updateDepartIDToPatient();

        // 初始化本地病患表准备同步，将标志位置“0”
        int ret = patientDaoMapper.initForSync();
        syncMessage.append(String.format("本地初始化记录：%d 条", ret));

        // 自动同步：住院号相同，姓名，科室，床号相同
        // 同步逻辑1：更新住院号、科室、床号、姓名全部相同的记录，并标注flag=1
        int syncCount = patientDaoMapper.syncEqual();
        syncMessage.append(String.format("更新逻辑1： %d 条", syncCount));

        // 自动同步：住院号不相同，姓名相同，科室，床号不相同，该病患更换了其它的科室，例如：从一科换去二科
        // 同步逻辑2：更新住院号、姓名相同的记录，并标注flag=1
        syncCount = patientDaoMapper.syncEqualForHospitalAndName();
        syncMessage.append(String.format("更新逻辑2： %d 条", syncCount));

        // 自动同步：住院号不同，科室，床号相同，姓名不同，认为是一个新增的病患记录
        // 同步逻辑3：新增住院号、科室、床号相同的记录，作为新病患同步，并标注flag=2
        syncCount = patientDaoMapper.syncNewHospitalId();
        syncMessage.append(String.format("更新逻辑3： %d 条", syncCount));

        // 手动：住院号相同，科室、床号、姓名都不相同，无法判断这个住院号后面三个数据是什么原因发生了变化
        // 冲突逻辑1：住院号相同、科室、床号、姓名都不相同的记录，作为冲突数据，并标注flag=3
        List<ftSyncConflictVo> syncPatientVos = patientDaoMapper.syncConflictOnlyHospitalEqual();
        syncMessage.append(String.format("冲突逻辑1： %d 条", syncPatientVos.size()));
        for (ftSyncConflictVo vo : syncPatientVos) {
            syncConflictDaoMapper.insert(vo);
        }

        // 手动：不知道该情况是按照新病患处理还是旧的病患更新住院号信息
        // 冲突逻辑2：住院号不相同、科室、床号、姓名都相同的记录，作为冲突数据，并标注flag=3
        List<ftSyncConflictVo> syncPatientVoList = patientDaoMapper.syncConflictOtherAllEqual();
        syncMessage.append(String.format("冲突逻辑2： %d 条", syncPatientVoList.size()));
        for (ftSyncConflictVo ftSyncConflictVo : syncPatientVoList) {
            syncConflictDaoMapper.insert(ftSyncConflictVo);
        }

        int msgTotal = syncPatientVos.size() + syncPatientVoList.size();

        // 如果有冲突发送一条消息
        if (msgTotal > 0) {
            FtNotifyDao notifyDao = new FtNotifyDao();
            // 类型为病患同步冲突
            notifyDao.setMessageType(1);
            // 广播类型
            notifyDao.setScope(0);
            // 消息内容
            notifyDao.setMessageBody("有 " + msgTotal + " 条病患数据同步时发生冲突");
            notifyDao.setCreateAt(new Date());

            notifyDaoMapper.insert(notifyDao);

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
