package com.ruoyi.system.fantang.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtInvoiceDao;
import com.ruoyi.system.fantang.domain.FtReturnDao;
import com.ruoyi.system.fantang.service.IFtInvoiceDaoService;
import com.ruoyi.system.fantang.service.IFtReturnDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 回款登记Controller
 *
 * @author ft
 * @date 2021-01-25
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/returnManage")
public class FtReturnDaoController extends BaseController {

    private final IFtReturnDaoService iFtReturnDaoService;

    private final IFtInvoiceDaoService iFtInvoiceDaoService;

    /**
     * 查询回款登记列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:returnManage:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtReturnDao ftReturnDao) {
        startPage();
        List<FtReturnDao> list = iFtReturnDaoService.queryList(ftReturnDao);
        return getDataTable(list);
    }

    /**
     * 导出回款登记列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:returnManage:export')")
    @Log(title = "回款登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtReturnDao ftReturnDao) {
        List<FtReturnDao> list = iFtReturnDaoService.queryList(ftReturnDao);
        ExcelUtil<FtReturnDao> util = new ExcelUtil<FtReturnDao>(FtReturnDao.class);
        return util.exportExcel(list, "returnManage");
    }

    /**
     * 获取回款登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:returnManage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iFtReturnDaoService.getById(id));
    }

    /**
     * 新增回款登记
     */
    @PreAuthorize("@ss.hasPermi('fantang:returnManage:add')")
    @Log(title = "回款登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtReturnDao ftReturnDao) {
        return toAjax(iFtReturnDaoService.save(ftReturnDao) ? 1 : 0);
    }

    /**
     * 修改回款登记
     */
    @PreAuthorize("@ss.hasPermi('fantang:returnManage:edit')")
    @Log(title = "回款登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtReturnDao ftReturnDao) {
        return toAjax(iFtReturnDaoService.updateById(ftReturnDao) ? 1 : 0);
    }

    /**
     * 删除回款登记
     */
    @PreAuthorize("@ss.hasPermi('fantang:returnManage:remove')")
    @Log(title = "回款登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtReturnDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }

    @PostMapping("/addToReturn")
    public AjaxResult addToReturn(@RequestBody JSONObject params) {
        System.out.println(params);

        // 回款金额
        BigDecimal returnPrice = params.getBigDecimal("returnPrice");

        // 开票金额
        BigDecimal invoiceAmount = params.getBigDecimal("invoiceAmount");

        // 余额
        BigDecimal nowBalancePrice;

        // 发票 id
        Long id = params.getLong("id");

        if (returnPrice.compareTo(invoiceAmount) > 0) {
            return AjaxResult.error("该次回款超出开票金额");
        }

        // 查找该发票上次的回款记录
        FtReturnDao lastReturn = iFtReturnDaoService.selectLastReturn(id);

        if (lastReturn == null) {
            // 余额
            nowBalancePrice = invoiceAmount.subtract(returnPrice);
        } else {
            BigDecimal lastBalancePrice = lastReturn.getBalancePrice();
            if (lastBalancePrice.compareTo(returnPrice) < 0) {
                return AjaxResult.error("该次回款超出开票金额");
            } else {
                nowBalancePrice = lastBalancePrice.subtract(returnPrice);
            }
        }


        FtReturnDao ftReturnDao = new FtReturnDao();
        ftReturnDao.setInvoiceId(id);
        ftReturnDao.setReturnPrice(returnPrice);
        ftReturnDao.setVoucherUrl(params.getString("voucherUrl"));
        ftReturnDao.setReturnAt(new Date());
        ftReturnDao.setBalancePrice(nowBalancePrice);

        if (nowBalancePrice.compareTo(new BigDecimal(0)) == 0) {
            FtInvoiceDao ftInvoiceDao = new FtInvoiceDao();
            ftInvoiceDao.setId(id);
            ftInvoiceDao.setInvoiceType(1);
            iFtInvoiceDaoService.updateById(ftInvoiceDao);
        }

        iFtReturnDaoService.save(ftReturnDao);

        return AjaxResult.success("回款成功");
    }

    @GetMapping("/getReturnByInvoice/{id}")
    public TableDataInfo getReturnByInvoice(@PathVariable Long id) {
        startPage();
        QueryWrapper<FtReturnDao> wrapper = new QueryWrapper<>();
        wrapper.eq("invoice_id", id);
        List<FtReturnDao> list = iFtReturnDaoService.list(wrapper);
        return getDataTable(list);
    }
}
