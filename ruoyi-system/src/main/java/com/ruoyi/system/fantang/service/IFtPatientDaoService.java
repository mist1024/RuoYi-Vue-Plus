package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtPatientDao;
import com.ruoyi.system.fantang.vo.FtDepartVo;

/**
 * 病人管理Service接口
 *
 * @author ft
 * @date 2020-11-24
 */
public interface IFtPatientDaoService extends IService<FtPatientDao> {

    AjaxResult getReportMealsToday(String createAt, Long patientId);

    FtDepartVo getReportMealsByDepart(Long departId, String createAt);
}
