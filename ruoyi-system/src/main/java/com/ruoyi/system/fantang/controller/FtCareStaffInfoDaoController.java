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
import com.ruoyi.system.fantang.domain.FtCareStaffInfoDao;
import com.ruoyi.system.fantang.service.IFtCareStaffInfoDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 护工信息Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/careStaffInfo")
public class FtCareStaffInfoDaoController extends BaseController {

    private final IFtCareStaffInfoDaoService iFtCareStaffInfoDaoService;

    /**
     * 查询护工信息列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:careStaffInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtCareStaffInfoDao ftCareStaffInfoDao) {
        startPage();
        LambdaQueryWrapper<FtCareStaffInfoDao> lqw = Wrappers.lambdaQuery(ftCareStaffInfoDao);
        if (StringUtils.isNotBlank(ftCareStaffInfoDao.getName())) {
            lqw.like(FtCareStaffInfoDao::getName, ftCareStaffInfoDao.getName());
        }
        if (StringUtils.isNotBlank(ftCareStaffInfoDao.getCorpName())) {
            lqw.like(FtCareStaffInfoDao::getCorpName, ftCareStaffInfoDao.getCorpName());
        }
        if (StringUtils.isNotBlank(ftCareStaffInfoDao.getDepartList())) {
            lqw.eq(FtCareStaffInfoDao::getDepartList, ftCareStaffInfoDao.getDepartList());
        }
        List<FtCareStaffInfoDao> list = iFtCareStaffInfoDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出护工信息列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:careStaffInfo:export')")
    @Log(title = "护工信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtCareStaffInfoDao ftCareStaffInfoDao) {
        LambdaQueryWrapper<FtCareStaffInfoDao> lqw = new LambdaQueryWrapper<FtCareStaffInfoDao>(ftCareStaffInfoDao);
        List<FtCareStaffInfoDao> list = iFtCareStaffInfoDaoService.list(lqw);
        ExcelUtil<FtCareStaffInfoDao> util = new ExcelUtil<FtCareStaffInfoDao>(FtCareStaffInfoDao.class);
        return util.exportExcel(list, "careStaffInfo");
    }

    /**
     * 获取护工信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:careStaffInfo:query')")
    @GetMapping(value = "/{careStaffId}")
    public AjaxResult getInfo(@PathVariable("careStaffId") Long careStaffId) {
        return AjaxResult.success(iFtCareStaffInfoDaoService.getById(careStaffId));
    }

    /**
     * 新增护工信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:careStaffInfo:add')")
    @Log(title = "护工信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtCareStaffInfoDao ftCareStaffInfoDao) {
        return toAjax(iFtCareStaffInfoDaoService.save(ftCareStaffInfoDao) ? 1 : 0);
    }

    /**
     * 修改护工信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:careStaffInfo:edit')")
    @Log(title = "护工信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtCareStaffInfoDao ftCareStaffInfoDao) {
        return toAjax(iFtCareStaffInfoDaoService.updateById(ftCareStaffInfoDao) ? 1 : 0);
    }

    /**
     * 删除护工信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:careStaffInfo:remove')")
    @Log(title = "护工信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{careStaffIds}")
    public AjaxResult remove(@PathVariable Long[] careStaffIds) {
        return toAjax(iFtCareStaffInfoDaoService.removeByIds(Arrays.asList(careStaffIds)) ? 1 : 0);
    }
}
