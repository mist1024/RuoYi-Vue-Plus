package com.ruoyi.system.fantang.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtStaffDemandDao;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 员工报餐Service接口
 *
 * @author 陈智兴
 * @date 2020-12-07
 */
public interface IFtStaffDemandDaoService extends IService<FtStaffDemandDao> {

    AjaxResult getConfiguration(Long staffId);

    AjaxResult setDemandMode(Long id, Integer type, Boolean demandModeFlag);

    AjaxResult initDemandMode(Long staffId);
}
