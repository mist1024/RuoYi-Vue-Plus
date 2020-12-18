package com.ruoyi.winery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;
import java.util.Arrays;

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
import com.ruoyi.winery.domain.WineryCompanyRecord;
import com.ruoyi.winery.service.IWineryCompanyRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 酒庄厂家登记记录Controller
 *
 * @author ruoyi
 * @date 2020-12-18
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/winery/winery_company_record")
public class WineryCompanyRecordController extends BaseController {

    private final IWineryCompanyRecordService iWineryCompanyRecordService;

    /**
     * 查询酒庄厂家登记记录列表
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_company_record:list')")
    @GetMapping("/list")
    public TableDataInfo list(WineryCompanyRecord wineryCompanyRecord) {
        startPage();
        LambdaQueryWrapper<WineryCompanyRecord> lqw = Wrappers.lambdaQuery(wineryCompanyRecord);
        if (StringUtils.isNotBlank(wineryCompanyRecord.getStatus())) {
            lqw.eq(WineryCompanyRecord::getStatus, wineryCompanyRecord.getStatus());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getOpenid())) {
            lqw.eq(WineryCompanyRecord::getOpenid, wineryCompanyRecord.getOpenid());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getEmail())) {
            lqw.eq(WineryCompanyRecord::getEmail, wineryCompanyRecord.getEmail());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getPersonName())) {
            lqw.like(WineryCompanyRecord::getPersonName, wineryCompanyRecord.getPersonName());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getMobile())) {
            lqw.eq(WineryCompanyRecord::getMobile, wineryCompanyRecord.getMobile());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getWineryName())) {
            lqw.like(WineryCompanyRecord::getWineryName, wineryCompanyRecord.getWineryName());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getBuildTime())) {
            lqw.eq(WineryCompanyRecord::getBuildTime, wineryCompanyRecord.getBuildTime());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getRegion())) {
            lqw.eq(WineryCompanyRecord::getRegion, wineryCompanyRecord.getRegion());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getAddress())) {
            lqw.eq(WineryCompanyRecord::getAddress, wineryCompanyRecord.getAddress());
        }
        if (wineryCompanyRecord.getWineryArea() != null) {
            lqw.eq(WineryCompanyRecord::getWineryArea, wineryCompanyRecord.getWineryArea());
        }
        if (wineryCompanyRecord.getBuildArea() != null) {
            lqw.eq(WineryCompanyRecord::getBuildArea, wineryCompanyRecord.getBuildArea());
        }
        if (wineryCompanyRecord.getWineryStatus() != null) {
            lqw.eq(WineryCompanyRecord::getWineryStatus, wineryCompanyRecord.getWineryStatus());
        }
        if (wineryCompanyRecord.getPlantArea() != null) {
            lqw.eq(WineryCompanyRecord::getPlantArea, wineryCompanyRecord.getPlantArea());
        }
        if (wineryCompanyRecord.getPlantWeight() != null) {
            lqw.eq(WineryCompanyRecord::getPlantWeight, wineryCompanyRecord.getPlantWeight());
        }
        if (wineryCompanyRecord.getSoilType() != null) {
            lqw.eq(WineryCompanyRecord::getSoilType, wineryCompanyRecord.getSoilType());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getRedVariety())) {
            lqw.eq(WineryCompanyRecord::getRedVariety, wineryCompanyRecord.getRedVariety());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getWhiteVariety())) {
            lqw.eq(WineryCompanyRecord::getWhiteVariety, wineryCompanyRecord.getWhiteVariety());
        }
        if (wineryCompanyRecord.getIrrigationType() != null) {
            lqw.eq(WineryCompanyRecord::getIrrigationType, wineryCompanyRecord.getIrrigationType());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getFermentationProcess())) {
            lqw.eq(WineryCompanyRecord::getFermentationProcess, wineryCompanyRecord.getFermentationProcess());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getContainer())) {
            lqw.eq(WineryCompanyRecord::getContainer, wineryCompanyRecord.getContainer());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getClarificationMethod())) {
            lqw.eq(WineryCompanyRecord::getClarificationMethod, wineryCompanyRecord.getClarificationMethod());
        }
        if (wineryCompanyRecord.getAnnualOutput() != null) {
            lqw.eq(WineryCompanyRecord::getAnnualOutput, wineryCompanyRecord.getAnnualOutput());
        }
        if (wineryCompanyRecord.getStock() != null) {
            lqw.eq(WineryCompanyRecord::getStock, wineryCompanyRecord.getStock());
        }
        if (wineryCompanyRecord.getBucketCount() != null) {
            lqw.eq(WineryCompanyRecord::getBucketCount, wineryCompanyRecord.getBucketCount());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getMainPrice())) {
            lqw.eq(WineryCompanyRecord::getMainPrice, wineryCompanyRecord.getMainPrice());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getSalesMode())) {
            lqw.eq(WineryCompanyRecord::getSalesMode, wineryCompanyRecord.getSalesMode());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getAwards())) {
            lqw.eq(WineryCompanyRecord::getAwards, wineryCompanyRecord.getAwards());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getAwardInformation())) {
            lqw.eq(WineryCompanyRecord::getAwardInformation, wineryCompanyRecord.getAwardInformation());
        }
        if (StringUtils.isNotBlank(wineryCompanyRecord.getSlogan())) {
            lqw.eq(WineryCompanyRecord::getSlogan, wineryCompanyRecord.getSlogan());
        }
        List<WineryCompanyRecord> list = iWineryCompanyRecordService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出酒庄厂家登记记录列表
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_company_record:export')")
    @Log(title = "酒庄厂家登记记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WineryCompanyRecord wineryCompanyRecord) {
        LambdaQueryWrapper<WineryCompanyRecord> lqw = new LambdaQueryWrapper<WineryCompanyRecord>(wineryCompanyRecord);
        List<WineryCompanyRecord> list = iWineryCompanyRecordService.list(lqw);
        ExcelUtil<WineryCompanyRecord> util = new ExcelUtil<WineryCompanyRecord>(WineryCompanyRecord.class);
        return util.exportExcel(list, "winery_company_record");
    }

    /**
     * 获取酒庄厂家登记记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_company_record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(iWineryCompanyRecordService.getById(id));
    }

    /**
     * 新增酒庄厂家登记记录
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_company_record:add')")
    @Log(title = "酒庄厂家登记记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WineryCompanyRecord wineryCompanyRecord) {
        return toAjax(iWineryCompanyRecordService.save(wineryCompanyRecord) ? 1 : 0);
    }

    /**
     * 修改酒庄厂家登记记录
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_company_record:edit')")
    @Log(title = "酒庄厂家登记记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WineryCompanyRecord wineryCompanyRecord) {
        return toAjax(iWineryCompanyRecordService.updateById(wineryCompanyRecord) ? 1 : 0);
    }

    /**
     * 删除酒庄厂家登记记录
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_company_record:remove')")
    @Log(title = "酒庄厂家登记记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(iWineryCompanyRecordService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
