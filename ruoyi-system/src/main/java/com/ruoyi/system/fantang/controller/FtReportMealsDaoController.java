package com.ruoyi.system.fantang.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.entity.ReportMealsDayEntity;
import com.ruoyi.system.fantang.service.IFtReportMealsDaoService;
import com.ruoyi.system.fantang.vo.FtReportMealVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
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
     * 查询指定用户上一次结算的日期，并通过这个日期计算未结算的天数
     */
    @GetMapping("/getLastSettlementDate/{patientId}")
    public AjaxResult getLastSettlementDate(@PathVariable("patientId") Long patientId) {

        QueryWrapper<FtReportMealsDao> wrapper = new QueryWrapper<>();
        wrapper.eq("patient_id", patientId);
        wrapper.orderByDesc("settlement_at");
        wrapper.last("limit 1");
        FtReportMealsDao ftReportMealsDao = iFtReportMealsDaoService.getOne(wrapper);
        Date createAt = ftReportMealsDao.getCreateAt();
        Date settlementAt = ftReportMealsDao.getSettlementAt();
        ReportMealsDayEntity reportMealsDayEntity = new ReportMealsDayEntity();
        if (settlementAt == null) {
            long betweenDays = DateUtil.between(createAt, new Date(), DateUnit.DAY);
            reportMealsDayEntity.setDays(betweenDays);
            reportMealsDayEntity.setLastCreateDate(createAt);
            return AjaxResult.success(reportMealsDayEntity);
        }
        long days = DateUtil.between(settlementAt, new Date(), DateUnit.DAY);

        reportMealsDayEntity.setSettlementAt(settlementAt);
        reportMealsDayEntity.setDays(days);

        return AjaxResult.success(reportMealsDayEntity);
    }


    /**
     * 查询所有报餐列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:meals:list')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(FtReportMealVo ftReportMealsDao) {
        startPage();
        List<FtReportMealVo> list = iFtReportMealsDaoService.listAll();
        return getDataTable(list);
    }

    /**
     * 查询所有未付费报餐列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:meals:list')")
    @GetMapping("/listNoPay")
    public TableDataInfo listNoPay(FtReportMealVo ftReportMealsDao) {
        startPage();
        List<FtReportMealVo> list = iFtReportMealsDaoService.listNoPay();
        return getDataTable(list);
    }

    /**
     * 查询所有已付费报餐列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:meals:list')")
    @GetMapping("/listPayoff")
    public TableDataInfo listPayoff(FtReportMealVo ftReportMealsDao) {
        startPage();

        List<FtReportMealVo> list = iFtReportMealsDaoService.listPayoff();
        return getDataTable(list);
    }


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
     * 计算两个日期之间的未结算数据
     *
     * @param dao
     * @return
     */
    @GetMapping("/countBillingBetween")
    public AjaxResult countBillingBetween(ReportMealsDayEntity dao) {
        return AjaxResult.success(iFtReportMealsDaoService.countBillingBetween(dao));
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
