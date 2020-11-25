package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtFoodDefaultDao;
import com.ruoyi.system.fantang.service.IFtFoodDefaultDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 默认报餐管理Controller
 *
 * @author ft
 * @date 2020-11-25
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/foodDefault")
public class FtFoodDefaultDaoController extends BaseController {

    private final IFtFoodDefaultDaoService iFtFoodDefaultDaoService;

    /**
     * 查询默认报餐管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDefault:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtFoodDefaultDao ftFoodDefaultDao) {
        startPage();
        LambdaQueryWrapper<FtFoodDefaultDao> lqw = Wrappers.lambdaQuery(ftFoodDefaultDao);
        if (ftFoodDefaultDao.getType() != null) {
            lqw.eq(FtFoodDefaultDao::getType, ftFoodDefaultDao.getType());
        }
        List<FtFoodDefaultDao> list = iFtFoodDefaultDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出默认报餐管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDefault:export')")
    @Log(title = "默认报餐管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtFoodDefaultDao ftFoodDefaultDao) {
        LambdaQueryWrapper<FtFoodDefaultDao> lqw = new LambdaQueryWrapper<FtFoodDefaultDao>(ftFoodDefaultDao);
        List<FtFoodDefaultDao> list = iFtFoodDefaultDaoService.list(lqw);
        ExcelUtil<FtFoodDefaultDao> util = new ExcelUtil<FtFoodDefaultDao>(FtFoodDefaultDao.class);
        return util.exportExcel(list, "foodDefault");
    }

    /**
     * 获取默认报餐管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDefault:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iFtFoodDefaultDaoService.getById(id));
    }

    /**
     * 新增默认报餐管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDefault:add')")
    @Log(title = "默认报餐管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtFoodDefaultDao ftFoodDefaultDao) {
        return toAjax(iFtFoodDefaultDaoService.save(ftFoodDefaultDao) ? 1 : 0);
    }

    /**
     * 修改默认报餐管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDefault:edit')")
    @Log(title = "默认报餐管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtFoodDefaultDao ftFoodDefaultDao) {
        return toAjax(iFtFoodDefaultDaoService.updateById(ftFoodDefaultDao) ? 1 : 0);
    }

    /**
     * 删除默认报餐管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDefault:remove')")
    @Log(title = "默认报餐管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtFoodDefaultDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
