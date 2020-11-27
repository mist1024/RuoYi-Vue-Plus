package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtWeekMenuDao;
import com.ruoyi.system.fantang.service.IFtWeekMenuDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 每周菜单Controller
 *
 * @author ft
 * @date 2020-11-27
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/weekMenu")
public class FtWeekMenuDaoController extends BaseController {

    private final IFtWeekMenuDaoService iFtWeekMenuDaoService;

    /**
     * 查询每周菜单列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:weekMenu:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtWeekMenuDao ftWeekMenuDao) {
        startPage();
        LambdaQueryWrapper<FtWeekMenuDao> lqw = Wrappers.lambdaQuery(ftWeekMenuDao);
        if (ftWeekMenuDao.getDinnerType() != null) {
            lqw.eq(FtWeekMenuDao::getDinnerType, ftWeekMenuDao.getDinnerType());
        }
        if (ftWeekMenuDao.getWeekday() != null) {
            lqw.eq(FtWeekMenuDao::getWeekday, ftWeekMenuDao.getWeekday());
        }
        if (ftWeekMenuDao.getFlag() != null) {
            lqw.eq(FtWeekMenuDao::getFlag, ftWeekMenuDao.getFlag());
        }
        List<FtWeekMenuDao> list = iFtWeekMenuDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出每周菜单列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:weekMenu:export')")
    @Log(title = "每周菜单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtWeekMenuDao ftWeekMenuDao) {
        LambdaQueryWrapper<FtWeekMenuDao> lqw = new LambdaQueryWrapper<FtWeekMenuDao>(ftWeekMenuDao);
        List<FtWeekMenuDao> list = iFtWeekMenuDaoService.list(lqw);
        ExcelUtil<FtWeekMenuDao> util = new ExcelUtil<FtWeekMenuDao>(FtWeekMenuDao.class);
        return util.exportExcel(list, "weekMenu");
    }

    /**
     * 获取每周菜单详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:weekMenu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iFtWeekMenuDaoService.getById(id));
    }

    /**
     * 新增每周菜单
     */
    @PreAuthorize("@ss.hasPermi('fantang:weekMenu:add')")
    @Log(title = "每周菜单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtWeekMenuDao ftWeekMenuDao) {
        return toAjax(iFtWeekMenuDaoService.save(ftWeekMenuDao) ? 1 : 0);
    }

    /**
     * 修改每周菜单
     */
    @PreAuthorize("@ss.hasPermi('fantang:weekMenu:edit')")
    @Log(title = "每周菜单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtWeekMenuDao ftWeekMenuDao) {
        return toAjax(iFtWeekMenuDaoService.updateById(ftWeekMenuDao) ? 1 : 0);
    }

    /**
     * 删除每周菜单
     */
    @PreAuthorize("@ss.hasPermi('fantang:weekMenu:remove')")
    @Log(title = "每周菜单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtWeekMenuDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
