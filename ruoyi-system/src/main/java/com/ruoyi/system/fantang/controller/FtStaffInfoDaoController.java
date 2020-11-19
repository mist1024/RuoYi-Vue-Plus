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
import com.ruoyi.system.fantang.domain.FtStaffInfoDao;
import com.ruoyi.system.fantang.service.IFtStaffInfoDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 员工管理Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/staffInfo")
public class FtStaffInfoDaoController extends BaseController {

    private final IFtStaffInfoDaoService iFtStaffInfoDaoService;

    /**
     * 查询员工管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtStaffInfoDao ftStaffInfoDao) {
        startPage();
        LambdaQueryWrapper<FtStaffInfoDao> lqw = Wrappers.lambdaQuery(ftStaffInfoDao);
        if (StringUtils.isNotBlank(ftStaffInfoDao.getName())) {
            lqw.like(FtStaffInfoDao::getName, ftStaffInfoDao.getName());
        }
        if (StringUtils.isNotBlank(ftStaffInfoDao.getPost())) {
            lqw.eq(FtStaffInfoDao::getPost, ftStaffInfoDao.getPost());
        }
        if (StringUtils.isNotBlank(ftStaffInfoDao.getRole())) {
            lqw.eq(FtStaffInfoDao::getRole, ftStaffInfoDao.getRole());
        }
        List<FtStaffInfoDao> list = iFtStaffInfoDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出员工管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:export')")
    @Log(title = "员工管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtStaffInfoDao ftStaffInfoDao) {
        LambdaQueryWrapper<FtStaffInfoDao> lqw = new LambdaQueryWrapper<FtStaffInfoDao>(ftStaffInfoDao);
        List<FtStaffInfoDao> list = iFtStaffInfoDaoService.list(lqw);
        ExcelUtil<FtStaffInfoDao> util = new ExcelUtil<FtStaffInfoDao>(FtStaffInfoDao.class);
        return util.exportExcel(list, "staffInfo");
    }

    /**
     * 获取员工管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:query')")
    @GetMapping(value = "/{staffId}")
    public AjaxResult getInfo(@PathVariable("staffId") Long staffId) {
        return AjaxResult.success(iFtStaffInfoDaoService.getById(staffId));
    }

    /**
     * 新增员工管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:add')")
    @Log(title = "员工管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtStaffInfoDao ftStaffInfoDao) {
        return toAjax(iFtStaffInfoDaoService.save(ftStaffInfoDao) ? 1 : 0);
    }

    /**
     * 修改员工管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:edit')")
    @Log(title = "员工管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtStaffInfoDao ftStaffInfoDao) {
        return toAjax(iFtStaffInfoDaoService.updateById(ftStaffInfoDao) ? 1 : 0);
    }

    /**
     * 删除员工管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:remove')")
    @Log(title = "员工管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{staffIds}")
    public AjaxResult remove(@PathVariable Long[] staffIds) {
        return toAjax(iFtStaffInfoDaoService.removeByIds(Arrays.asList(staffIds)) ? 1 : 0);
    }
}
