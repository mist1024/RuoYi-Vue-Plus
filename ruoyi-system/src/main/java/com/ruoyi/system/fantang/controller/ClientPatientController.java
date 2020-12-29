package com.ruoyi.system.fantang.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.service.IFtPatientDaoService;
import com.ruoyi.system.fantang.vo.FtPatientVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/client_api/patient")
public class ClientPatientController extends BaseController {

    @Autowired
    private IFtPatientDaoService iFtPatientDaoService;

    @GetMapping("/getReportMealsToday/{patientId}")
    public AjaxResult getReportMealsToday(@PathVariable("patientId") Long patientId) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String createAt = sdf.format(new Date());

        return AjaxResult.success(iFtPatientDaoService.getReportMealsToday(createAt, patientId));
    }
}
