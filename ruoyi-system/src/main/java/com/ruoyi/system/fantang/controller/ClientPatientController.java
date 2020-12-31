package com.ruoyi.system.fantang.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtFoodDao;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.domain.FtStaffInfoDao;
import com.ruoyi.system.fantang.service.*;
import com.ruoyi.system.fantang.vo.FtDepartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/client_api/patient")
public class ClientPatientController extends BaseController {

    @Autowired
    private IFtPatientDaoService iFtPatientDaoService;

    @Autowired
    private IFtStaffInfoDaoService iFtStaffInfoDaoService;

    @Autowired
    private IFtDepartDaoService iFtDepartDaoService;

    @Autowired
    private IFtReportMealsDaoService iFtReportMealsDaoService;

    @Autowired
    private IFtFoodDemandDaoService iFtFoodDemandDaoService;

    @Autowired
    private IFtNutritionFoodDaoService iFftNutritionFoodDaoService;

    @Autowired
    private IFtFoodDaoService iftFoodDaoService;

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
    public AjaxResult getAllDepart() {

        return AjaxResult.success(iFtDepartDaoService.list());
    }

    /**
     * 更改默认报餐部门
     */
    @PutMapping("/updateDepart")
    public AjaxResult updateDepart(@RequestBody JSONObject params) {
        Long staffId = params.getLong("staffId");
        String departId = params.getString("departId");

        UpdateWrapper<FtStaffInfoDao> wrapper = new UpdateWrapper<>();
        wrapper.eq("staff_id", staffId);
        FtStaffInfoDao staffInfoDao = new FtStaffInfoDao();
        staffInfoDao.setDeptList(departId);

        return AjaxResult.success(iFtStaffInfoDaoService.update(staffInfoDao, wrapper));
    }

    /**
     * 批量更新报餐记录
     */
    @PutMapping("/batchUpdateReportMeals")
    public AjaxResult batchUpdateReportMeals(@RequestBody List<FtReportMealsDao> reportMealsDaoList) {
        return AjaxResult.success(iFtReportMealsDaoService.updateBatchById(reportMealsDaoList));
    }

    /**
     * 更新指定病患的报餐记录
     */
    @PutMapping("/updateReportMeals")
    public AjaxResult updateReportMeals(@RequestBody FtReportMealsDao ftReportMealsDao) {
        return AjaxResult.success(iFtReportMealsDaoService.updateById(ftReportMealsDao));
    }

    /**
     * 更新指定病患默认报餐数据
     */
    @PutMapping("/updateFoodDemand")
    public AjaxResult updateFoodDemand(@RequestBody FtFoodDemandDao ftFoodDemandDao) {
        return AjaxResult.success(iFtFoodDemandDaoService.updateById(ftFoodDemandDao));
    }

    /**
     * 获取营养配餐配置信息
     */
    @GetMapping("getNutritionFood")
    public AjaxResult getNutritionFood() {
        return AjaxResult.success(iFftNutritionFoodDaoService.list());
    }

    /**
     * 获取菜品清单
     */
    @GetMapping("/getFoodPrice")
    public AjaxResult getFoodPrice() {
        QueryWrapper<FtFoodDao> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 1);
        return AjaxResult.success(iftFoodDaoService.list(wrapper));
    }
}
