package com.ruoyi.system.fantang.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtStaffInfoDao;
import com.ruoyi.system.fantang.service.IFtDepartDaoService;
import com.ruoyi.system.fantang.service.IFtPatientDaoService;
import com.ruoyi.system.fantang.service.IFtStaffInfoDaoService;
import com.ruoyi.system.fantang.vo.FtDepartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/client_api/patient")
public class ClientPatientController extends BaseController {

    @Autowired
    private IFtPatientDaoService iFtPatientDaoService;

    @Autowired
    private IFtStaffInfoDaoService iFtStaffInfoDaoService;

    @Autowired
    private IFtDepartDaoService iFtDepartDaoService;

    /**
     * 根据病人获取今日报餐信息
     */
    @GetMapping("/getReportMealsToday/{patientId}")
    public AjaxResult getReportMealsToday(@PathVariable("patientId") Long patientId) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String createAt = sdf.format(new Date());

        return AjaxResult.success(iFtPatientDaoService.getReportMealsToday(createAt, patientId));
    }

    /**
     * 根据病人获取次日报餐信息
     */
    @GetMapping("/getReportMealsTomorrow/{patientId}")
    public AjaxResult getReportMealsTomorrow(@PathVariable("patientId") Long patientId) {

        DateTime tomorrow = DateUtil.tomorrow();
        String formatDate = DateUtil.formatDate(tomorrow);

        return AjaxResult.success(iFtPatientDaoService.getReportMealsToday(formatDate, patientId));
    }

    /**
     * 根据科室获取次日报餐信息
     */
    @GetMapping("/getReportMealsByDepart/{departId}")
    public AjaxResult getReportMealsByDepart(@PathVariable("departId") Long departId) {

        DateTime tomorrow = DateUtil.tomorrow();
        String formatDate = DateUtil.formatDate(tomorrow);

        FtDepartVo ftDepartVo = iFtPatientDaoService.getReportMealsByDepart(departId, formatDate);

        return AjaxResult.success(ftDepartVo);
    }

    /**
     * 获取护工默认部门信息
     */
    @GetMapping("/getDepartInfo/{staffId}")
    public AjaxResult getDepartInfo(@PathVariable("staffId") Long staffId) {

        FtStaffInfoDao departInfo = iFtStaffInfoDaoService.getDepartInfo(staffId);

        return AjaxResult.success(departInfo);
    }

    /**
     * 获取部门列表
     */
    @GetMapping("/getAllDepart")
    public AjaxResult getAllDepart(){

        return AjaxResult.success(iFtDepartDaoService.list());
    }
}
