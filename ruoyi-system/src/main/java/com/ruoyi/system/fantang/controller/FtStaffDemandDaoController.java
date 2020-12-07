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
import com.ruoyi.system.fantang.domain.FtStaffDemandDao;
import com.ruoyi.system.fantang.service.IFtConfigDaoService;
import com.ruoyi.system.fantang.service.IFtStaffDemandDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 员工报餐Controller
 *
 * @author ft
 * @date 2020-12-07
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/staffDemand")
public class FtStaffDemandDaoController extends BaseController {

    private final IFtStaffDemandDaoService iFtStaffDemandDaoService;

    private final IFtConfigDaoService iFtConfigDaoService;


    /**
     * 获取用餐时间
     */
    @GetMapping("/getDinnerTimeSetting")
    public AjaxResult getDinnerTimeSetting() {
        return AjaxResult.success(iFtConfigDaoService.getDinnerTimeSetting());
    }

    /**
     * 查询员工报餐列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffDemand:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtStaffDemandDao ftStaffDemandDao) {
        startPage();
        LambdaQueryWrapper<FtStaffDemandDao> lqw = Wrappers.lambdaQuery(ftStaffDemandDao);
        if (ftStaffDemandDao.getStaffId() != null) {
            lqw.eq(FtStaffDemandDao::getStaffId, ftStaffDemandDao.getStaffId());
        }
        if (StringUtils.isNotBlank(ftStaffDemandDao.getFoods())) {
            lqw.eq(FtStaffDemandDao::getFoods, ftStaffDemandDao.getFoods());
        }
        if (ftStaffDemandDao.getType() != null) {
            lqw.eq(FtStaffDemandDao::getType, ftStaffDemandDao.getType());
        }
        if (ftStaffDemandDao.getCreateAt() != null) {
            lqw.eq(FtStaffDemandDao::getCreateAt, ftStaffDemandDao.getCreateAt());
        }
        if (ftStaffDemandDao.getUpdateAt() != null) {
            lqw.eq(FtStaffDemandDao::getUpdateAt, ftStaffDemandDao.getUpdateAt());
        }
        if (ftStaffDemandDao.getUpdateFrom() != null) {
            lqw.eq(FtStaffDemandDao::getUpdateFrom, ftStaffDemandDao.getUpdateFrom());
        }
        if (StringUtils.isNotBlank(ftStaffDemandDao.getOrderInfo())) {
            lqw.eq(FtStaffDemandDao::getOrderInfo, ftStaffDemandDao.getOrderInfo());
        }
        if (ftStaffDemandDao.getDemandMode() != null) {
            lqw.eq(FtStaffDemandDao::getDemandMode, ftStaffDemandDao.getDemandMode());
        }
        if (ftStaffDemandDao.getStopFlag() != null) {
            lqw.eq(FtStaffDemandDao::getStopFlag, ftStaffDemandDao.getStopFlag());
        }
        List<FtStaffDemandDao> list = iFtStaffDemandDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出员工报餐列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffDemand:export')")
    @Log(title = "员工报餐", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtStaffDemandDao ftStaffDemandDao) {
        LambdaQueryWrapper<FtStaffDemandDao> lqw = new LambdaQueryWrapper<FtStaffDemandDao>(ftStaffDemandDao);
        List<FtStaffDemandDao> list = iFtStaffDemandDaoService.list(lqw);
        ExcelUtil<FtStaffDemandDao> util = new ExcelUtil<FtStaffDemandDao>(FtStaffDemandDao.class);
        return util.exportExcel(list, "staffDemand");
    }

    /**
     * 获取员工报餐详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffDemand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iFtStaffDemandDaoService.getById(id));
    }

    /**
     * 新增员工报餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffDemand:add')")
    @Log(title = "员工报餐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtStaffDemandDao ftStaffDemandDao) {
        return toAjax(iFtStaffDemandDaoService.save(ftStaffDemandDao) ? 1 : 0);
    }

    /**
     * 修改员工报餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffDemand:edit')")
    @Log(title = "员工报餐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtStaffDemandDao ftStaffDemandDao) {
        return toAjax(iFtStaffDemandDaoService.updateById(ftStaffDemandDao) ? 1 : 0);
    }

    /**
     * 删除员工报餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffDemand:remove')")
    @Log(title = "员工报餐", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtStaffDemandDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
