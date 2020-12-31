package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtPatientDao;
import com.ruoyi.system.fantang.vo.FtDepartVo;
import com.ruoyi.system.fantang.vo.FtPatientVo;

import java.util.List;

/**
 * 病人管理Service接口
 *
 * @author ft
 * @date 2020-11-24
 */
public interface IFtPatientDaoService extends IService<FtPatientDao> {

    AjaxResult getReportMealsToday(String createAt, Long patientId);

    List<FtPatientVo> getReportMealsByDepart(Long departId, String createAt);
}
