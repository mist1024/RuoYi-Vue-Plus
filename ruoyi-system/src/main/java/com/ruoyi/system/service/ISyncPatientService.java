package com.ruoyi.system.service;

import com.ruoyi.system.fantang.domain.FtRemotePatientDao;

import java.util.List;

/**
 * 远程病患数据同步服务层接口
 */
public interface ISyncPatientService {
    public Integer insertToLocalSync(List<FtRemotePatientDao> remotePatientDaoList);
}
