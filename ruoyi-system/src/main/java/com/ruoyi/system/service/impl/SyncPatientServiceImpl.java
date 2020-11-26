package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.fantang.domain.FtRemotePatientDao;
import com.ruoyi.system.fantang.mapper.FtSyncPatientDaoMapper;
import com.ruoyi.system.service.ISyncPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@DataSource(value = DataSourceType.MASTER)
@Service
public class SyncPatientServiceImpl implements ISyncPatientService {
    @Autowired
    private FtSyncPatientDaoMapper syncPatientDaoMapper;

    @Override
    public Integer insertToLocalSync(List<FtRemotePatientDao> remotePatientDaoList) {
        for (FtRemotePatientDao dao : remotePatientDaoList) {
            syncPatientDaoMapper.insert(dao);
        }
        return null;
    }
}
