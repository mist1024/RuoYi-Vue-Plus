package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtSpecialDemandDao;
import com.ruoyi.system.fantang.service.IFtSpecialDemandDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 特殊用餐管理Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/demand")
public class FtSpecialDemandDaoController extends BaseController {

    private final IFtSpecialDemandDaoService iFtSpecialDemandDaoService;

    /**
     * 查询特殊用餐管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:demand:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtSpecialDemandDao ftSpecialDemandDao) {
        startPage();
        LambdaQueryWrapper<FtSpecialDemandDao> lqw = Wrappers.lambdaQuery(ftSpecialDemandDao);
        if (ftSpecialDemandDao.getType() != null) {
            lqw.eq(FtSpecialDemandDao::getType, ftSpecialDemandDao.getType());
        }
        if (ftSpecialDemandDao.getCreateAt() != null) {
            lqw.eq(FtSpecialDemandDao::getCreateAt, ftSpecialDemandDao.getCreateAt());
        }
        if (ftSpecialDemandDao.getPrice() != null) {
            lqw.eq(FtSpecialDemandDao::getPrice, ftSpecialDemandDao.getPrice());
        }
        if (ftSpecialDemandDao.getTerm() != null) {
            lqw.eq(FtSpecialDemandDao::getTerm, ftSpecialDemandDao.getTerm());
        }
        List<FtSpecialDemandDao> list = iFtSpecialDemandDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出特殊用餐管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:demand:export')")
    @Log(title = "特殊用餐管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtSpecialDemandDao ftSpecialDemandDao) {
        LambdaQueryWrapper<FtSpecialDemandDao> lqw = new LambdaQueryWrapper<FtSpecialDemandDao>(ftSpecialDemandDao);
        List<FtSpecialDemandDao> list = iFtSpecialDemandDaoService.list(lqw);
        ExcelUtil<FtSpecialDemandDao> util = new ExcelUtil<FtSpecialDemandDao>(FtSpecialDemandDao.class);
        return util.exportExcel(list, "demand");
    }

    /**
     * 获取特殊用餐管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:demand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iFtSpecialDemandDaoService.getById(id));
    }

    /**
     * 新增特殊用餐管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:demand:add')")
    @Log(title = "特殊用餐管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtSpecialDemandDao ftSpecialDemandDao) {
        return toAjax(iFtSpecialDemandDaoService.save(ftSpecialDemandDao) ? 1 : 0);
    }

    /**
     * 修改特殊用餐管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:demand:edit')")
    @Log(title = "特殊用餐管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtSpecialDemandDao ftSpecialDemandDao) {
        return toAjax(iFtSpecialDemandDaoService.updateById(ftSpecialDemandDao) ? 1 : 0);
    }

    /**
     * 删除特殊用餐管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:demand:remove')")
    @Log(title = "特殊用餐管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtSpecialDemandDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
