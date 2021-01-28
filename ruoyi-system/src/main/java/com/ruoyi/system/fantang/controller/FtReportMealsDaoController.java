package com.ruoyi.system.fantang.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtPrepaymentDao;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.entity.ReportMealsDayEntity;
import com.ruoyi.system.fantang.service.IFtPrepaymentDaoService;
import com.ruoyi.system.fantang.service.IFtReportMealsDaoService;
import com.ruoyi.system.fantang.vo.FtReportMealVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.ruoyi.common.core.domain.AjaxResult.success;

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

    @Autowired
    private IFtPrepaymentDaoService prepaymentDaoService;


    /**
     * 查询指定用户上一次结算的日期，并通过这个日期计算未结算的天数
     */
    @GetMapping("/getLastSettlementDate/{patientId}")
    public AjaxResult getLastSettlementDate(@PathVariable("patientId") Long patientId) {
        // 初始化一个返回对象
        AjaxResult result = AjaxResult.success();

        // 获取该病患的预付费数据
        FtPrepaymentDao prepaymentDao = prepaymentDaoService.getByPatientId(patientId);
        result.put("prepayment", prepaymentDao);

        // 获取最近一次已结算的报餐记录，如果首次结算则返回第一条已用餐的记录
        FtReportMealsDao reportMealsDao = iFtReportMealsDaoService.getLastReportMeals(patientId);

        // 获取用餐日期
        Date diningAt = reportMealsDao.getDiningAt();
        // 获取结算日期
        Date settlementAt = reportMealsDao.getSettlementAt();
        ReportMealsDayEntity reportMealsDayEntity = new ReportMealsDayEntity();

        // 如果首次结算
        if (settlementAt == null) {
            // 计算第一条已用餐的用餐时间与现在相差多少天
            long betweenDays = DateUtil.between(diningAt, new Date(), DateUnit.DAY);
            reportMealsDayEntity.setDays(betweenDays);
            reportMealsDayEntity.setLastCreateDate(diningAt);
            result.put("reportMeals", reportMealsDayEntity);

            return result;
        }

        //计算上次结算日期与现在相差多少天
        long days = DateUtil.between(settlementAt, new Date(), DateUnit.DAY);
        reportMealsDayEntity.setSettlementAt(settlementAt);
        reportMealsDayEntity.setDays(days);
        result.put("reportMeals", reportMealsDayEntity);

        return result;
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

    @GetMapping("/listStatistics")
    public TableDataInfo listStatistics(FtReportMealsDao ftReportMealsDao) {

        startPage();

        return getDataTable(iFtReportMealsDaoService.listNutrition(ftReportMealsDao));
    }

    @PreAuthorize("@ss.hasPermi('fantang:meals:list')")
    @GetMapping("/listMealsWithInSettle")
    public TableDataInfo listMealsWithInSettle(FtReportMealsDao ftReportMealsDao) {
        startPage();
        List<FtReportMealsDao> list = iFtReportMealsDaoService.listMealsWithInSettle(ftReportMealsDao);
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
        return success(iFtReportMealsDaoService.countBillingBetween(dao));
    }

    /**
     * 获取报餐管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:meals:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(iFtReportMealsDaoService.getById(id));
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


    @GetMapping("/listPatientReportMeals")
    public TableDataInfo listPatientReportMeals(FtReportMealVo ftReportMealsDao) {
        startPage();
        Date createAt = ftReportMealsDao.getCreateAt();

        if (createAt != null) {
            ftReportMealsDao.setBeginOfDay(createAt);
            ftReportMealsDao.setEndOfDay(createAt);
        }

        List<FtReportMealVo> list = iFtReportMealsDaoService.listPatientReportMeals(ftReportMealsDao);
        return getDataTable(list);

    }
}
