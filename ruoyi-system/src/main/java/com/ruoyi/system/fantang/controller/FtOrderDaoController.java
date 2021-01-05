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
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.service.IFtOrderDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 订单管理Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/order")
public class FtOrderDaoController extends BaseController {

    private final IFtOrderDaoService iFtOrderDaoService;

    /**
     * 查询订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtOrderDao ftOrderDao) {
        startPage();
        LambdaQueryWrapper<FtOrderDao> lqw = Wrappers.lambdaQuery(ftOrderDao);
        if (ftOrderDao.getOrderType() != null) {
            lqw.eq(FtOrderDao::getOrderType, ftOrderDao.getOrderType());
        }
        if (ftOrderDao.getTotalPrice() != null) {
            lqw.eq(FtOrderDao::getTotalPrice, ftOrderDao.getTotalPrice());
        }
        if (ftOrderDao.getDiscount() != null) {
            lqw.eq(FtOrderDao::getDiscount, ftOrderDao.getDiscount());
        }
        if (ftOrderDao.getReceipts() != null) {
            lqw.eq(FtOrderDao::getReceipts, ftOrderDao.getReceipts());
        }
        if (ftOrderDao.getCreateAt() != null) {
            lqw.eq(FtOrderDao::getCreateAt, ftOrderDao.getCreateAt());
        }
        if (StringUtils.isNotBlank(ftOrderDao.getOrderSrc())) {
            lqw.eq(FtOrderDao::getOrderSrc, ftOrderDao.getOrderSrc());
        }
        if (ftOrderDao.getCurrentPrice() != null) {
            lqw.eq(FtOrderDao::getCurrentPrice, ftOrderDao.getCurrentPrice());
        }
        if (ftOrderDao.getPayType() != null) {
            lqw.eq(FtOrderDao::getPayType, ftOrderDao.getPayType());
        }
        if (ftOrderDao.getWriteOffAt() != null) {
            lqw.eq(FtOrderDao::getWriteOffAt, ftOrderDao.getWriteOffAt());
        }
        List<FtOrderDao> list = iFtOrderDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:order:export')")
    @Log(title = "订单管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtOrderDao ftOrderDao) {
        LambdaQueryWrapper<FtOrderDao> lqw = new LambdaQueryWrapper<FtOrderDao>(ftOrderDao);
        List<FtOrderDao> list = iFtOrderDaoService.list(lqw);
        ExcelUtil<FtOrderDao> util = new ExcelUtil<FtOrderDao>(FtOrderDao.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 获取订单管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId) {
        return AjaxResult.success(iFtOrderDaoService.getById(orderId));
    }

    /**
     * 新增订单管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:order:add')")
    @Log(title = "订单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtOrderDao ftOrderDao) {
        return toAjax(iFtOrderDaoService.save(ftOrderDao) ? 1 : 0);
    }

    /**
     * 修改订单管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:order:edit')")
    @Log(title = "订单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtOrderDao ftOrderDao) {
        return toAjax(iFtOrderDaoService.updateById(ftOrderDao) ? 1 : 0);
    }

    /**
     * 删除订单管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:order:remove')")
    @Log(title = "订单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds) {
        return toAjax(iFtOrderDaoService.removeByIds(Arrays.asList(orderIds)) ? 1 : 0);
    }
}
