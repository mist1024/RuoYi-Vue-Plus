package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtStaffSubsidyDao;
import com.ruoyi.system.fantang.mapper.FtStaffSubsidyDaoMapper;
import com.ruoyi.system.fantang.service.IFtStaffSubsidyDaoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 补贴流水查看Service业务层处理
 *
 * @author ft
 * @date 2020-11-19
 */
@Service
public class FtStaffSubsidyDaoServiceImpl extends ServiceImpl<FtStaffSubsidyDaoMapper, FtStaffSubsidyDao> implements IFtStaffSubsidyDaoService {

    @Override
    public Integer insertBatchStaffSubsidy(List<FtStaffSubsidyDao> ftStaffSubsidyDaoList) {
        return this.baseMapper.insertBatchStaffSubsidy(ftStaffSubsidyDaoList);
    }

    @Override
    public void reBalance(String subsidyType, Integer maxBalance) {
        this.baseMapper.insertReBalance(subsidyType, maxBalance);
    }
}
