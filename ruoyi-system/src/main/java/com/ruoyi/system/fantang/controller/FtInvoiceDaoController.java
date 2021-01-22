package com.ruoyi.system.fantang.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtInvoiceDao;
import com.ruoyi.system.fantang.domain.FtReturnDao;
import com.ruoyi.system.fantang.domain.FtSettlementDao;
import com.ruoyi.system.fantang.service.IFtInvoiceDaoService;
import com.ruoyi.system.fantang.service.IFtReturnDaoService;
import com.ruoyi.system.fantang.service.IFtSettlementDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 财务收费开票Controller
 *
 * @author ft
 * @date 2020-12-08
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/invoice")
public class FtInvoiceDaoController extends BaseController {

    private final IFtInvoiceDaoService iFtInvoiceDaoService;

    private final IFtSettlementDaoService settSettlementDaoService;

    private final IFtReturnDaoService ftReturnDaoService;

    /**
     * 查询财务收费开票列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtInvoiceDao ftInvoiceDao) {
        startPage();
        LambdaQueryWrapper<FtInvoiceDao> lqw = Wrappers.lambdaQuery(ftInvoiceDao);

        if (ftInvoiceDao.getInvoiceType() != null) {
            lqw.eq(FtInvoiceDao::getInvoiceType, ftInvoiceDao.getInvoiceType());
        }

        List<FtInvoiceDao> list = iFtInvoiceDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出财务收费开票列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:export')")
    @Log(title = "财务收费开票", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtInvoiceDao ftInvoiceDao) {
        LambdaQueryWrapper<FtInvoiceDao> lqw = new LambdaQueryWrapper<FtInvoiceDao>(ftInvoiceDao);
        List<FtInvoiceDao> list = iFtInvoiceDaoService.list(lqw);
        ExcelUtil<FtInvoiceDao> util = new ExcelUtil<FtInvoiceDao>(FtInvoiceDao.class);
        return util.exportExcel(list, "invoice");
    }

    /**
     * 获取财务收费开票详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iFtInvoiceDaoService.getById(id));
    }

    /**
     * 新增财务收费开票
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:add')")
    @Log(title = "财务收费开票", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtInvoiceDao ftInvoiceDao) {
        return toAjax(iFtInvoiceDaoService.save(ftInvoiceDao) ? 1 : 0);
    }

    /**
     * 修改财务收费开票
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:edit')")
    @Log(title = "财务收费开票", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtInvoiceDao ftInvoiceDao) {
        return toAjax(iFtInvoiceDaoService.updateById(ftInvoiceDao) ? 1 : 0);
    }

    /**
     * 删除财务收费开票
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:remove')")
    @Log(title = "财务收费开票", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtInvoiceDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }

    @PostMapping("/addToInvoice")
    @Transactional
    public AjaxResult addToInvoice(@RequestBody JSONObject params) {

        // 应收
        BigDecimal payable = params.getBigDecimal("payable");
        // 实收
        BigDecimal receipts = params.getBigDecimal("receipts");
        // 收款方式
        String type = params.getString("type");
        // 发票号
        String invoiceNum = params.getString("invoiceNum");
        // 发票名
        String invoiceName = params.getString("invoiceName");
        // 税号
        String taxId = params.getString("taxId");
        // 跟踪回款
        Integer invoiceType = params.getInteger("invoiceType");
        // 开票金额
        BigDecimal invoiceAmount = params.getBigDecimal("invoiceAmount");

        FtInvoiceDao invoiceDao = new FtInvoiceDao();
        Date today = new Date();
        invoiceDao.setCreateAt(today);
        invoiceDao.setPayable(payable);
        invoiceDao.setReceipts(receipts);
        invoiceDao.setCollectionType(type);
        invoiceDao.setInvoiceNum(invoiceNum);
        invoiceDao.setInvoiceName(invoiceName);
        invoiceDao.setTaxId(taxId);
        invoiceDao.setInvoiceType(invoiceType);
        invoiceDao.setInvoiceAmount(invoiceAmount);
        iFtInvoiceDaoService.save(invoiceDao);

        // 跟踪回款
        if (invoiceType == 2) {
            FtReturnDao ftReturnDao = new FtReturnDao();
            ftReturnDao.setInvoiceId(invoiceDao.getId());
            ftReturnDao.setReturnAt(new Date());
            ftReturnDao.setReturnFlag(0);

            ftReturnDaoService.save(ftReturnDao);
        }

        FtSettlementDao settlementDao = new FtSettlementDao();
        settlementDao.setSettleId(params.getLong("settleId"));
        settlementDao.setInvoiceId(invoiceDao.getId());
        settlementDao.setInvoiceFlag(true);
        settSettlementDaoService.updateById(settlementDao);

        return AjaxResult.success("已开票");
    }
}
