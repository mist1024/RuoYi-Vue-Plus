package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtCateringDao;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.service.IFtCateringDaoService;
import com.ruoyi.system.fantang.service.IFtFoodDemandDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 配餐功能Controller
 *
 * @author ft
 * @date 2020-12-07
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/catering")
public class FtCateringDaoController extends BaseController {

    private final IFtCateringDaoService iFtCateringDaoService;

    private final IFtFoodDemandDaoService iFtFoodDemandDaoService;

    /**
     * 查询配餐功能列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtCateringDao ftCateringDao) {
        startPage();
        List<FtCateringDao> list = iFtCateringDaoService.listNewFormatter(ftCateringDao);
        return getDataTable(list);
    }

    /**
     * 导出配餐功能列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:export')")
    @Log(title = "配餐功能", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtCateringDao ftCateringDao) {
        LambdaQueryWrapper<FtCateringDao> lqw = new LambdaQueryWrapper<FtCateringDao>(ftCateringDao);
        List<FtCateringDao> list = iFtCateringDaoService.list(lqw);
        ExcelUtil<FtCateringDao> util = new ExcelUtil<FtCateringDao>(FtCateringDao.class);
        return util.exportExcel(list, "catering");
    }

    /**
     * 获取配餐功能详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        FtCateringDao ftCateringDao = iFtCateringDaoService.getByIdNewFormatter(id);
        return AjaxResult.success(ftCateringDao);
    }

    /**
     * 新增配餐功能
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:add')")
    @Log(title = "配餐功能", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody FtCateringDao ftCateringDao) {

        // 添加一个病人新增 4 条营养配餐记录
        List<FtCateringDao> ftCateringList = iFtCateringDaoService.addNutritionCatering(ftCateringDao);

        // 更新病人报餐配置表
        iFtFoodDemandDaoService.updateDayFoodDemand(ftCateringList);

        return AjaxResult.success("新增成功");
    }

    /**
     * 修改配餐功能
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:edit')")
    @Log(title = "配餐功能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtCateringDao ftCateringDao) {

        Long patientId = ftCateringDao.getPatientId();
        Integer type = ftCateringDao.getType();
        Long number = ftCateringDao.getNumber();
        Boolean flag = ftCateringDao.getFlag();

        QueryWrapper<FtFoodDemandDao> wrapper = new QueryWrapper<>();
        wrapper.eq("patient_id", patientId);
        wrapper.eq("type", type);
        FtFoodDemandDao foodDemandDao = iFtFoodDemandDaoService.getOne(wrapper);
        foodDemandDao.setNutritionFoodId(number);
        foodDemandDao.setUpdateAt(new Date());
        foodDemandDao.setNutritionFoodFlag(flag);
        iFtFoodDemandDaoService.updateById(foodDemandDao);

        return toAjax(iFtCateringDaoService.updateById(ftCateringDao) ? 1 : 0);
    }

    /**
     * 删除配餐功能
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:remove')")
    @Log(title = "配餐功能", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(iFtCateringDaoService.deleteByPatientId(ids));
    }

    /**
     * 作废营养配餐数据
     */
    @PutMapping("/cancel/{ids}")
    @Transactional
    public AjaxResult cancel(@PathVariable Long[] ids) {
        System.out.println(Arrays.toString(ids));

        // 根据病人 id 修改营养配餐启用标志
        iFtCateringDaoService.cancelByPatientId(ids);

        // 根据病人 id 修改报餐配置表营养餐启用标志
        iFtFoodDemandDaoService.cancelNutritionByPatientId(ids);

        return AjaxResult.success("已作废");
    }

    /**
     * 根据病人 id 查找营养配餐数据
     */
    @GetMapping("/getByPatient/{patientId}")
    public AjaxResult getByPatient(@PathVariable Long patientId) {
        QueryWrapper<FtCateringDao> wrapper = new QueryWrapper<>();
        wrapper.eq("patient_id", patientId);
        wrapper.eq("type", 1);
        return AjaxResult.success(iFtCateringDaoService.getOne(wrapper));
    }

    /**
     * 拷贝并新增
     */
    @PostMapping("/copyAndAdd")
    public AjaxResult copyAndAdd(@RequestBody FtCateringDao ftCateringDao) {

        System.out.println(ftCateringDao);


        return null;
    }
}
