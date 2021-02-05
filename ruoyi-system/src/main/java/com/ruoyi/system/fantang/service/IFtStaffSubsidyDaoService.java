package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.fantang.domain.FtStaffSubsidyDao;

import java.util.List;

/**
 * 补贴流水查看Service接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface IFtStaffSubsidyDaoService extends IService<FtStaffSubsidyDao> {

    Integer insertBatchStaffSubsidy(List<FtStaffSubsidyDao> ftStaffSubsidyDaoList);

    void reBalance(String subsidyType, Integer maxBalance);
}
