package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtStaffInfoDao;

import java.util.List;

/**
 * 员工管理Service接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface IFtStaffInfoDaoService extends IService<FtStaffInfoDao> {

    List<FtStaffInfoDao> selectStaffInfoWithDepart(FtStaffInfoDao ftStaffInfoDao);

    AjaxResult login(String tel, String password);

    AjaxResult logout(Long staffId);
}
