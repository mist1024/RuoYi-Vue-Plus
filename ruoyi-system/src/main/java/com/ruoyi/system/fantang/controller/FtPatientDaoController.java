package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtPatientDao;
import com.ruoyi.system.fantang.service.IFtFoodDemandDaoService;
import com.ruoyi.system.fantang.service.IFtPatientDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 病人管理Controller
 *
 * @author ft
 * @date 2020-11-24
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/patient")
public class FtPatientDaoController extends BaseController {

    private final IFtPatientDaoService iFtPatientDaoService;

    private final IFtFoodDemandDaoService iFtFoodDemandDaoService;

    /**
     * 查询病人管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtPatientDao ftPatientDao) {
        startPage();
        LambdaQueryWrapper<FtPatientDao> lqw = Wrappers.lambdaQuery(ftPatientDao);
        if (StringUtils.isNotBlank(ftPatientDao.getName())) {
            lqw.like(FtPatientDao::getName, ftPatientDao.getName());
        }
        if (StringUtils.isNotBlank(ftPatientDao.getBedId())) {
            lqw.eq(FtPatientDao::getBedId, ftPatientDao.getBedId());
        }
        if (StringUtils.isNotBlank(ftPatientDao.getHospitalId())) {
            lqw.eq(FtPatientDao::getHospitalId, ftPatientDao.getHospitalId());
        }
        List<FtPatientDao> list = iFtPatientDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 根据 departId 查询病人列表
     */
    @GetMapping("/selectPatientByDepartId/{departId}")
    public AjaxResult selectPatientByDepartId(@PathVariable("departId") Long departId) {
        QueryWrapper<FtPatientDao> wrapper = new QueryWrapper<>();
        wrapper.eq("depart_id", departId);
        List<FtPatientDao> list = iFtPatientDaoService.list(wrapper);
        return AjaxResult.success(list);
    }

    /**
     * 根据 departId 查询所有没有营养配餐的病人列表
     * @author 陈智兴
     */
    @GetMapping("/selectNoCateringByDepartId/{departId}")
    public AjaxResult selectNoCateringByDepartId(@PathVariable("departId") Long departId) {
        List<FtPatientDao> list = iFtPatientDaoService.selectNoCateringByDepartId(departId);
        return AjaxResult.success(list);
    }


    /**
     * 根据 patientId 查询病人床号
     */
    @GetMapping("/getBedIdById/{patientId}")
    public AjaxResult getBedIdById(@PathVariable("patientId") Long patientId) {
        String bedId = iFtPatientDaoService.getById(patientId).getBedId();
        return AjaxResult.success(bedId);
    }

    /**
     * 导出病人管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:export')")
    @Log(title = "病人管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtPatientDao ftPatientDao) {
        LambdaQueryWrapper<FtPatientDao> lqw = new LambdaQueryWrapper<FtPatientDao>(ftPatientDao);
        List<FtPatientDao> list = iFtPatientDaoService.list(lqw);
        ExcelUtil<FtPatientDao> util = new ExcelUtil<FtPatientDao>(FtPatientDao.class);
        return util.exportExcel(list, "patient");
    }

    /**
     * 获取病人管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:query')")
    @GetMapping(value = "/{patientId}")
    public AjaxResult getInfo(@PathVariable("patientId") Long patientId) {
        return AjaxResult.success(iFtPatientDaoService.getById(patientId));
    }

    /**
     * 新增病人管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:add')")
    @Log(title = "病人管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtPatientDao ftPatientDao) {

        ftPatientDao.setCreateAt(new Date());
        ftPatientDao.setOffFlag(0);
        iFtPatientDaoService.save(ftPatientDao);

        iFtFoodDemandDaoService.GenerateOrderByPatientId(ftPatientDao.getPatientId());

        return AjaxResult.success("已添加");
    }

    /**
     * 修改病人管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:edit')")
    @Log(title = "病人管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtPatientDao ftPatientDao) {
        return toAjax(iFtPatientDaoService.updateById(ftPatientDao) ? 1 : 0);
    }

    /**
     * 删除病人管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:remove')")
    @Log(title = "病人管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{patientIds}")
    public AjaxResult remove(@PathVariable Long[] patientIds) {
        return toAjax(iFtPatientDaoService.removeByIds(Arrays.asList(patientIds)) ? 1 : 0);
    }
}
