package com.ruoyi.system.fantang.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtPrepaymentDao;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.domain.FtSettleDao;
import com.ruoyi.system.fantang.entity.ReportMealsPriceEntity;
import com.ruoyi.system.fantang.entity.SettleEntity;
import com.ruoyi.system.fantang.service.IFtInvoiceDaoService;
import com.ruoyi.system.fantang.service.IFtPrepaymentDaoService;
import com.ruoyi.system.fantang.service.IFtReportMealsDaoService;
import com.ruoyi.system.fantang.service.IFtSettleDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 结算报Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/settle")
public class FtSettleDaoController extends BaseController {

    private final IFtSettleDaoService iFtSettleDaoService;

    private final IFtReportMealsDaoService iFtReportMealsDaoService;

    private final IFtPrepaymentDaoService iFtPrepaymentDaoService;

    private final IFtInvoiceDaoService iFtInvoiceDaoService;


    /**
     * 显示报餐信息，包含日期，正餐与营养餐的总价格
     */
    @PostMapping("/showMealsWithSelect")
    public AjaxResult showMealsWithSelect(@RequestBody SettleEntity settlement) {

        // 病人 id
        Long patientId = settlement.getPatientId();

        // 上次结算 / 用餐日期
        Date lastBillingDate = settlement.getLastBillingDate();

        // 用户选择结算日期
        Date selectBillingDate = settlement.getSelectBillingDate();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 根据病人 id ，上次结算日期，选择日期查询病人非营养餐记录
        QueryWrapper<FtReportMealsDao> reportMealsWrapper = new QueryWrapper<>();
        reportMealsWrapper
                .eq("patient_id", patientId)
                .eq("dining_flag",1)
                .between("dining_at", sdf.format(lastBillingDate), DateUtil.endOfDay(selectBillingDate));
        Integer pageNum = settlement.getPageNum();
        Integer pageSize = settlement.getPageSize();
        IPage<FtReportMealsDao> reportMealsList = iFtReportMealsDaoService.listPage(reportMealsWrapper, pageNum, pageSize);

        ReportMealsPriceEntity reportMealsPrice = iFtReportMealsDaoService.sumTotalPrice(patientId, DateUtil.beginOfDay(lastBillingDate), DateUtil.endOfDay(selectBillingDate));

        Map<String, Object> data = new HashMap<>(2);
        data.put("reportMealsList", reportMealsList);
        data.put("reportMealsPrice", reportMealsPrice);

        return AjaxResult.success(data);
    }

    @GetMapping("/showAllMealsWithNoPay")
        public AjaxResult showAllMealsWithNoPay(@RequestParam("patientId") Long patientId, @RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize) {

        // 查找该病人所有已用餐未结算记录
        QueryWrapper<FtReportMealsDao> wrapper = new QueryWrapper<>();
        wrapper.eq("patient_id", patientId);
        wrapper.eq("dining_flag",1);
        wrapper.eq("settlement_flag",0);

        IPage<FtReportMealsDao> reportMealsList = iFtReportMealsDaoService.listPage(wrapper, pageNum, pageSize);
        ReportMealsPriceEntity reportMealsPrice = iFtReportMealsDaoService.sumAllTotalPrice(patientId);

        Map<String, Object> data = new HashMap<>(2);
        data.put("reportMealsList", reportMealsList);
        data.put("reportMealsPrice", reportMealsPrice);

        return AjaxResult.success(data);
    }

    /**
     * 新增结算报
     */
    @PostMapping("/addSettle")
    public AjaxResult addSettle(@RequestBody SettleEntity settlement) {

        // 病人 id
        Long patientId = settlement.getPatientId();

        // 上次结算 / 用餐日期
        Date lastBillingDate = settlement.getLastBillingDate();

        // 用户选择结算日期
        Date selectBillingDate = settlement.getSelectBillingDate();

        // 应收
        BigDecimal sumTotalPrice = settlement.getSumTotalPrice();

        // 实收
        BigDecimal netPeceipt = settlement.getNetPeceipt();

        // 操作用户
        String userName = settlement.getUserName();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 添加结算记录
        FtSettleDao ftSettleDao = new FtSettleDao();
        ftSettleDao.setReceipts(netPeceipt);
        ftSettleDao.setPatientId(patientId);
        Date nowDate = new Date();
        ftSettleDao.setSettleAt(nowDate);
        ftSettleDao.setOpera(userName);
        ftSettleDao.setPayable(sumTotalPrice);
        ftSettleDao.setReceipts(netPeceipt);

        //支付方式
        switch (settlement.getPayType()) {
            case 1:
                ftSettleDao.setType("现金");
                break;

            case 2:
                // 根据病人 id 查询预付费记录
                QueryWrapper<FtPrepaymentDao> prepaymentWrapper = new QueryWrapper<>();
                prepaymentWrapper.eq("patient_id", patientId);
                FtPrepaymentDao prepaymentDao = iFtPrepaymentDaoService.getOne(prepaymentWrapper);

                // 预付费扣费
                BigDecimal prepaid = prepaymentDao.getPrepaid();
                BigDecimal balance = prepaid.subtract(netPeceipt);
                prepaymentDao.setPrepaid(balance);
                iFtPrepaymentDaoService.updateById(prepaymentDao);

                ftSettleDao.setType("预付款冲减");
                break;
                
            case 3:
                ftSettleDao.setType("在线支付");
                break;
            case 4 :
                ftSettleDao.setType("银行汇款");
                break;
            case 5:
                ftSettleDao.setType("挂账");
                break;

            default:
        }
        

        iFtSettleDaoService.save(ftSettleDao);

        // 修改报餐信息
        UpdateWrapper<FtReportMealsDao> reportMealsWrapper = new UpdateWrapper<>();
        reportMealsWrapper.eq("patient_id", patientId);
        reportMealsWrapper.between("create_at", sdf.format(lastBillingDate), sdf.format(selectBillingDate));
        FtReportMealsDao reportMealsDao = new FtReportMealsDao();
        reportMealsDao.setSettlementFlag(1);
        reportMealsDao.setSettlementAt(nowDate);
        reportMealsDao.setSettlementBy(userName);
        reportMealsDao.setSettlementId(ftSettleDao.getSettleId());
        iFtReportMealsDaoService.update(reportMealsDao, reportMealsWrapper);

        return AjaxResult.success("已收费");
    }

    /**
     * 查询结算报列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtSettleDao ftSettleDao) {
        startPage();
        LambdaQueryWrapper<FtSettleDao> lqw = Wrappers.lambdaQuery(ftSettleDao);
        if (ftSettleDao.getSettleAt() != null) {
            lqw.eq(FtSettleDao::getSettleAt, ftSettleDao.getSettleAt());
        }
        if (ftSettleDao.getPrice() != null) {
            lqw.eq(FtSettleDao::getPrice, ftSettleDao.getPrice());
        }
        if (ftSettleDao.getPayable() != null) {
            lqw.eq(FtSettleDao::getPayable, ftSettleDao.getPayable());
        }
        if (ftSettleDao.getReceipts() != null) {
            lqw.eq(FtSettleDao::getReceipts, ftSettleDao.getReceipts());
        }
        if (StringUtils.isNotBlank(ftSettleDao.getType())) {
            lqw.eq(FtSettleDao::getType, ftSettleDao.getType());
        }
        if (ftSettleDao.getRefund() != null) {
            lqw.eq(FtSettleDao::getRefund, ftSettleDao.getRefund());
        }
        List<FtSettleDao> list = iFtSettleDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出结算报列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:export')")
    @Log(title = "结算报", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtSettleDao ftSettleDao) {
        LambdaQueryWrapper<FtSettleDao> lqw = new LambdaQueryWrapper<FtSettleDao>(ftSettleDao);
        List<FtSettleDao> list = iFtSettleDaoService.list(lqw);
        ExcelUtil<FtSettleDao> util = new ExcelUtil<FtSettleDao>(FtSettleDao.class);
        return util.exportExcel(list, "settle");
    }

    /**
     * 获取结算报详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:query')")
    @GetMapping(value = "/{settleId}")
    public AjaxResult getInfo(@PathVariable("settleId") Long settleId) {
        return AjaxResult.success(iFtSettleDaoService.getById(settleId));
    }

    /**
     * 新增结算报
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:add')")
    @Log(title = "结算报", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtSettleDao ftSettleDao) {
        ftSettleDao.setSettleAt(new Date());
        ftSettleDao.setReceipts(ftSettleDao.getNetPeceipt());

        Long patientId = ftSettleDao.getPatientId();
        Date lastBillingDate = ftSettleDao.getLastBillingDate();
        Date selectBillingDate = ftSettleDao.getSelectBillingDate();
        iFtSettleDaoService.save(ftSettleDao);
        Long settlementId = ftSettleDao.getSettleId();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        iFtReportMealsDaoService.settleMeals(settlementId, patientId, ft.format(lastBillingDate), ft.format(selectBillingDate));
        iFtSettleDaoService.updateList(settlementId, patientId, ft.format(lastBillingDate), ft.format(selectBillingDate));

        return AjaxResult.success("结算成功");
    }

    /**
     * 修改结算报
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:edit')")
    @Log(title = "结算报", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtSettleDao ftSettleDao) {
        return toAjax(iFtSettleDaoService.updateById(ftSettleDao) ? 1 : 0);
    }

    /**
     * 删除结算报
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:remove')")
    @Log(title = "结算报", businessType = BusinessType.DELETE)
    @DeleteMapping("/{settleIds}")
    public AjaxResult remove(@PathVariable Long[] settleIds) {
        return toAjax(iFtSettleDaoService.removeByIds(Arrays.asList(settleIds)) ? 1 : 0);
    }
}
