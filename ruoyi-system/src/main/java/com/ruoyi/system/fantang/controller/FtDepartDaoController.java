package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtDepartDao;
import com.ruoyi.system.fantang.service.IFtDepartDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 科室管理Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/depart")
public class FtDepartDaoController extends BaseController {

    private final IFtDepartDaoService iFtDepartDaoService;

    /**
     * 查询科室管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:depart:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtDepartDao ftDepartDao) {
        startPage();
        LambdaQueryWrapper<FtDepartDao> lqw = Wrappers.lambdaQuery(ftDepartDao);
        if (StringUtils.isNotBlank(ftDepartDao.getDepartName())) {
            lqw.like(FtDepartDao::getDepartName, ftDepartDao.getDepartName());
        }
        List<FtDepartDao> list = iFtDepartDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出科室管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:depart:export')")
    @Log(title = "科室管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtDepartDao ftDepartDao) {
        LambdaQueryWrapper<FtDepartDao> lqw = new LambdaQueryWrapper<FtDepartDao>(ftDepartDao);
        List<FtDepartDao> list = iFtDepartDaoService.list(lqw);
        ExcelUtil<FtDepartDao> util = new ExcelUtil<FtDepartDao>(FtDepartDao.class);
        return util.exportExcel(list, "depart");
    }

    /**
     * 获取科室管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:depart:query')")
    @GetMapping(value = "/{departId}")
    public AjaxResult getInfo(@PathVariable("departId") Long departId) {
        return AjaxResult.success(iFtDepartDaoService.getById(departId));
    }

    /**
     * 新增科室管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:depart:add')")
    @Log(title = "科室管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtDepartDao ftDepartDao) {
        return toAjax(iFtDepartDaoService.save(ftDepartDao) ? 1 : 0);
    }

    /**
     * 修改科室管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:depart:edit')")
    @Log(title = "科室管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtDepartDao ftDepartDao) {
        return toAjax(iFtDepartDaoService.updateById(ftDepartDao) ? 1 : 0);
    }

    /**
     * 删除科室管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:depart:remove')")
    @Log(title = "科室管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{departIds}")
    public AjaxResult remove(@PathVariable Long[] departIds) {
        return toAjax(iFtDepartDaoService.removeByIds(Arrays.asList(departIds)) ? 1 : 0);
    }
}
