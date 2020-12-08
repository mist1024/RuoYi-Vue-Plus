package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.fantang.domain.FtInvoiceDao;
import com.ruoyi.system.fantang.service.IFtInvoiceDaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 财务收费开票Controller
 * 
 * @author ft
 * @date 2020-12-08
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/invoice" )
public class FtInvoiceDaoController extends BaseController {

    private final IFtInvoiceDaoService iFtInvoiceDaoService;

    /**
     * 查询财务收费开票列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtInvoiceDao ftInvoiceDao)
    {
        startPage();
        LambdaQueryWrapper<FtInvoiceDao> lqw = Wrappers.lambdaQuery(ftInvoiceDao);
        List<FtInvoiceDao> list = iFtInvoiceDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出财务收费开票列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:export')" )
    @Log(title = "财务收费开票" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(FtInvoiceDao ftInvoiceDao) {
        LambdaQueryWrapper<FtInvoiceDao> lqw = new LambdaQueryWrapper<FtInvoiceDao>(ftInvoiceDao);
        List<FtInvoiceDao> list = iFtInvoiceDaoService.list(lqw);
        ExcelUtil<FtInvoiceDao> util = new ExcelUtil<FtInvoiceDao>(FtInvoiceDao. class);
        return util.exportExcel(list, "invoice" );
    }

    /**
     * 获取财务收费开票详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iFtInvoiceDaoService.getById(id));
    }

    /**
     * 新增财务收费开票
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:add')" )
    @Log(title = "财务收费开票" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtInvoiceDao ftInvoiceDao) {
        return toAjax(iFtInvoiceDaoService.save(ftInvoiceDao) ? 1 : 0);
    }

    /**
     * 修改财务收费开票
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:edit')" )
    @Log(title = "财务收费开票" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtInvoiceDao ftInvoiceDao) {
        return toAjax(iFtInvoiceDaoService.updateById(ftInvoiceDao) ? 1 : 0);
    }

    /**
     * 删除财务收费开票
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoice:remove')" )
    @Log(title = "财务收费开票" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtInvoiceDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
