package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.fantang.domain.FtPatientDao;
import com.ruoyi.system.fantang.service.IFtPatientDaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 病人管理Controller
 * 
 * @author ft
 * @date 2020-11-24
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/patient" )
public class FtPatientDaoController extends BaseController {

    private final IFtPatientDaoService iFtPatientDaoService;

    /**
     * 查询病人管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtPatientDao ftPatientDao)
    {
        startPage();
        LambdaQueryWrapper<FtPatientDao> lqw = Wrappers.lambdaQuery(ftPatientDao);
        if (StringUtils.isNotBlank(ftPatientDao.getName())){
            lqw.like(FtPatientDao::getName ,ftPatientDao.getName());
        }
        if (StringUtils.isNotBlank(ftPatientDao.getBedId())){
            lqw.eq(FtPatientDao::getBedId ,ftPatientDao.getBedId());
        }
        if (StringUtils.isNotBlank(ftPatientDao.getHospitalId())){
            lqw.eq(FtPatientDao::getHospitalId ,ftPatientDao.getHospitalId());
        }
        List<FtPatientDao> list = iFtPatientDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出病人管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:export')" )
    @Log(title = "病人管理" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(FtPatientDao ftPatientDao) {
        LambdaQueryWrapper<FtPatientDao> lqw = new LambdaQueryWrapper<FtPatientDao>(ftPatientDao);
        List<FtPatientDao> list = iFtPatientDaoService.list(lqw);
        ExcelUtil<FtPatientDao> util = new ExcelUtil<FtPatientDao>(FtPatientDao. class);
        return util.exportExcel(list, "patient" );
    }

    /**
     * 获取病人管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:query')" )
    @GetMapping(value = "/{patientId}" )
    public AjaxResult getInfo(@PathVariable("patientId" ) Long patientId) {
        return AjaxResult.success(iFtPatientDaoService.getById(patientId));
    }

    /**
     * 新增病人管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:add')" )
    @Log(title = "病人管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtPatientDao ftPatientDao) {
        return toAjax(iFtPatientDaoService.save(ftPatientDao) ? 1 : 0);
    }

    /**
     * 修改病人管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:edit')" )
    @Log(title = "病人管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtPatientDao ftPatientDao) {
        return toAjax(iFtPatientDaoService.updateById(ftPatientDao) ? 1 : 0);
    }

    /**
     * 删除病人管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:patient:remove')" )
    @Log(title = "病人管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{patientIds}" )
    public AjaxResult remove(@PathVariable Long[] patientIds) {
        return toAjax(iFtPatientDaoService.removeByIds(Arrays.asList(patientIds)) ? 1 : 0);
    }
}
