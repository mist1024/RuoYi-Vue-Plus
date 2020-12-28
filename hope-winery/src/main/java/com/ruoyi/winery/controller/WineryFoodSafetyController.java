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
import com.ruoyi.winery.domain.WineryFoodSafety;
import com.ruoyi.winery.service.IWineryFoodSafetyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 食品安全详情Controller
 * 
 * @author ruoyi
 * @date 2020-12-28
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/winery/food_safety" )
public class WineryFoodSafetyController extends BaseController {

    private final IWineryFoodSafetyService iWineryFoodSafetyService;

    /**
     * 查询食品安全详情列表
     */
    @PreAuthorize("@ss.hasPermi('winery:food_safety:list')")
    @GetMapping("/list")
    public TableDataInfo list(WineryFoodSafety wineryFoodSafety)
    {
        startPage();
        LambdaQueryWrapper<WineryFoodSafety> lqw = Wrappers.lambdaQuery(wineryFoodSafety);
        if (wineryFoodSafety.getDeptId() != null){
            lqw.eq(WineryFoodSafety::getDeptId ,wineryFoodSafety.getDeptId());
        }
        if (StringUtils.isNotBlank(wineryFoodSafety.getProdLicense())){
            lqw.eq(WineryFoodSafety::getProdLicense ,wineryFoodSafety.getProdLicense());
        }
        if (StringUtils.isNotBlank(wineryFoodSafety.getStandardId())){
            lqw.eq(WineryFoodSafety::getStandardId ,wineryFoodSafety.getStandardId());
        }
        if (StringUtils.isNotBlank(wineryFoodSafety.getMakerName())){
            lqw.like(WineryFoodSafety::getMakerName ,wineryFoodSafety.getMakerName());
        }
        if (StringUtils.isNotBlank(wineryFoodSafety.getMakerAddress())){
            lqw.eq(WineryFoodSafety::getMakerAddress ,wineryFoodSafety.getMakerAddress());
        }
        if (StringUtils.isNotBlank(wineryFoodSafety.getMakerContact())){
            lqw.eq(WineryFoodSafety::getMakerContact ,wineryFoodSafety.getMakerContact());
        }
        if (StringUtils.isNotBlank(wineryFoodSafety.getIngredients())){
            lqw.eq(WineryFoodSafety::getIngredients ,wineryFoodSafety.getIngredients());
        }
        if (wineryFoodSafety.getPeriod() != null){
            lqw.eq(WineryFoodSafety::getPeriod ,wineryFoodSafety.getPeriod());
        }
        if (StringUtils.isNotBlank(wineryFoodSafety.getWineLevel())){
            lqw.eq(WineryFoodSafety::getWineLevel ,wineryFoodSafety.getWineLevel());
        }
        if (StringUtils.isNotBlank(wineryFoodSafety.getStorageMode())){
            lqw.eq(WineryFoodSafety::getStorageMode ,wineryFoodSafety.getStorageMode());
        }
        if (StringUtils.isNotBlank(wineryFoodSafety.getSupplier())){
            lqw.eq(WineryFoodSafety::getSupplier ,wineryFoodSafety.getSupplier());
        }
        if (wineryFoodSafety.getMakeDate() != null){
            lqw.eq(WineryFoodSafety::getMakeDate ,wineryFoodSafety.getMakeDate());
        }
        List<WineryFoodSafety> list = iWineryFoodSafetyService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出食品安全详情列表
     */
    @PreAuthorize("@ss.hasPermi('winery:food_safety:export')" )
    @Log(title = "食品安全详情" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(WineryFoodSafety wineryFoodSafety) {
        LambdaQueryWrapper<WineryFoodSafety> lqw = new LambdaQueryWrapper<WineryFoodSafety>(wineryFoodSafety);
        List<WineryFoodSafety> list = iWineryFoodSafetyService.list(lqw);
        ExcelUtil<WineryFoodSafety> util = new ExcelUtil<WineryFoodSafety>(WineryFoodSafety. class);
        return util.exportExcel(list, "food_safety" );
    }

    /**
     * 获取食品安全详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:food_safety:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iWineryFoodSafetyService.getById(id));
    }

    /**
     * 新增食品安全详情
     */
    @PreAuthorize("@ss.hasPermi('winery:food_safety:add')" )
    @Log(title = "食品安全详情" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WineryFoodSafety wineryFoodSafety) {
        return toAjax(iWineryFoodSafetyService.save(wineryFoodSafety) ? 1 : 0);
    }

    /**
     * 修改食品安全详情
     */
    @PreAuthorize("@ss.hasPermi('winery:food_safety:edit')" )
    @Log(title = "食品安全详情" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WineryFoodSafety wineryFoodSafety) {
        return toAjax(iWineryFoodSafetyService.updateById(wineryFoodSafety) ? 1 : 0);
    }

    /**
     * 删除食品安全详情
     */
    @PreAuthorize("@ss.hasPermi('winery:food_safety:remove')" )
    @Log(title = "食品安全详情" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iWineryFoodSafetyService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
