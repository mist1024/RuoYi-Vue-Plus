package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtRemotePatientDao;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

@Repository
public interface FtSyncPatientDaoMapper  extends BaseMapper<FtRemotePatientDao> {
    @Delete("truncate table ft_sync")
    void clearAll();
}
