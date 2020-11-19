package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.service.IFtReportMealsDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 报餐管理Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/meals")
public class FtReportMealsDaoController extends BaseController {

    private final IFtReportMealsDaoService iFtReportMealsDaoService;

    /**
     * 查询报餐管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:meals:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtReportMealsDao ftReportMealsDao) {
        startPage();
        LambdaQueryWrapper<FtReportMealsDao> lqw = Wrappers.lambdaQuery(ftReportMealsDao);
        if (ftReportMealsDao.getCreateAt() != null) {
            lqw.eq(FtReportMealsDao::getCreateAt, ftReportMealsDao.getCreateAt());
        }
        if (ftReportMealsDao.getType() != null) {
            lqw.eq(FtReportMealsDao::getType, ftReportMealsDao.getType());
        }
        if (ftReportMealsDao.getCreateBy() != null) {
            lqw.eq(FtReportMealsDao::getCreateBy, ftReportMealsDao.getCreateBy());
        }
        if (ftReportMealsDao.getPrice() != null) {
            lqw.eq(FtReportMealsDao::getPrice, ftReportMealsDao.getPrice());
        }
        List<FtReportMealsDao> list = iFtReportMealsDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出报餐管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:meals:export')")
    @Log(title = "报餐管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtReportMealsDao ftReportMealsDao) {
        LambdaQueryWrapper<FtReportMealsDao> lqw = new LambdaQueryWrapper<FtReportMealsDao>(ftReportMealsDao);
        List<FtReportMealsDao> list = iFtReportMealsDaoService.list(lqw);
        ExcelUtil<FtReportMealsDao> util = new ExcelUtil<FtReportMealsDao>(FtReportMealsDao.class);
        return util.exportExcel(list, "meals");
    }

    /**
     * 获取报餐管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:meals:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iFtReportMealsDaoService.getById(id));
    }

    /**
     * 新增报餐管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:meals:add')")
    @Log(title = "报餐管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtReportMealsDao ftReportMealsDao) {
        return toAjax(iFtReportMealsDaoService.save(ftReportMealsDao) ? 1 : 0);
    }

    /**
     * 修改报餐管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:meals:edit')")
    @Log(title = "报餐管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtReportMealsDao ftReportMealsDao) {
        return toAjax(iFtReportMealsDaoService.updateById(ftReportMealsDao) ? 1 : 0);
    }

    /**
     * 删除报餐管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:meals:remove')")
    @Log(title = "报餐管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtReportMealsDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
