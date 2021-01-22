package com.ruoyi.winery.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;

import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.winery.domain.AppOrder;
import com.ruoyi.winery.domain.goods.GoodsMain;
import com.ruoyi.winery.service.IAppOrderService;
import com.ruoyi.winery.service.IGoodsMainService;
import com.ruoyi.winery.vo.AppRequestRefundDetailVo;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
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
import com.ruoyi.winery.domain.AppOrderDetail;
import com.ruoyi.winery.service.IAppOrderDetailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import static com.ruoyi.common.core.domain.AjaxResult.error;

/**
 * 订单明细Controller
 *
 * @author ruoyi
 * @date 2021-01-18
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/winery/detail")
public class AppOrderDetailController extends BaseController {

    private final IAppOrderDetailService iAppOrderDetailService;

    @Autowired
    private IGoodsMainService goodsMainService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private IAppOrderService orderService;

    /**
     * 查询订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('winery:detail:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppOrderDetail appOrderDetail) {
        startPage();
        LambdaQueryWrapper<AppOrderDetail> lqw = Wrappers.lambdaQuery(appOrderDetail);
        if (appOrderDetail.getDeptId() != null) {
            lqw.eq(AppOrderDetail::getDeptId, appOrderDetail.getDeptId());
        }
        if (appOrderDetail.getUserId() != null) {
            lqw.eq(AppOrderDetail::getUserId, appOrderDetail.getUserId());
        }
        if (StringUtils.isNotBlank(appOrderDetail.getOrderId())) {
            lqw.eq(AppOrderDetail::getOrderId, appOrderDetail.getOrderId());
        }
        if (StringUtils.isNotBlank(appOrderDetail.getGoodsId())) {
            lqw.eq(AppOrderDetail::getGoodsId, appOrderDetail.getGoodsId());
        }
        if (appOrderDetail.getGoodsCount() != null) {
            lqw.eq(AppOrderDetail::getGoodsCount, appOrderDetail.getGoodsCount());
        }
        if (appOrderDetail.getStatus() != null) {
            lqw.eq(AppOrderDetail::getStatus, appOrderDetail.getStatus());
        }
        if (StringUtils.isNotBlank(appOrderDetail.getRefundNo())) {
            lqw.eq(AppOrderDetail::getRefundNo, appOrderDetail.getRefundNo());
        }
        if (appOrderDetail.getRefundTime() != null) {
            lqw.eq(AppOrderDetail::getRefundTime, appOrderDetail.getRefundTime());
        }
        lqw.orderByDesc(AppOrderDetail::getCreateTime);
        List<AppOrderDetail> list = iAppOrderDetailService.list(lqw);
        for (AppOrderDetail detail : list) {
            detail.setGoods(goodsMainService.getById(detail.getGoodsId()));
        }
        return getDataTable(list);
    }

    /**
     * 导出订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('winery:detail:export')")
    @Log(title = "订单明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AppOrderDetail appOrderDetail) {
        LambdaQueryWrapper<AppOrderDetail> lqw = new LambdaQueryWrapper<AppOrderDetail>(appOrderDetail);
        List<AppOrderDetail> list = iAppOrderDetailService.list(lqw);
        ExcelUtil<AppOrderDetail> util = new ExcelUtil<AppOrderDetail>(AppOrderDetail.class);
        return util.exportExcel(list, "detail");
    }

    /**
     * 获取订单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:detail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        AppOrderDetail detail = iAppOrderDetailService.getById(id);
        detail.setGoods(goodsMainService.getById(detail.getGoodsId()));
        return AjaxResult.success(detail);
    }

    /**
     * 新增订单明细
     */
    @PreAuthorize("@ss.hasPermi('winery:detail:add')")
    @Log(title = "订单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppOrderDetail appOrderDetail) {
        return toAjax(iAppOrderDetailService.save(appOrderDetail) ? 1 : 0);
    }

    /**
     * 修改订单明细
     */
    @PreAuthorize("@ss.hasPermi('winery:detail:edit')")
    @Log(title = "订单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppOrderDetail appOrderDetail) {
        return toAjax(iAppOrderDetailService.updateById(appOrderDetail) ? 1 : 0);
    }

    /**
     * 删除订单明细
     */
    @PreAuthorize("@ss.hasPermi('winery:detail:remove')")
    @Log(title = "订单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(iAppOrderDetailService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }

    @PreAuthorize("@ss.hasPermi('winery:detail:refund')")
    @Log(title = "退款", businessType = BusinessType.OTHER)
    @PostMapping("/refund/{id}")
    AjaxResult refund(@PathVariable String id) {
        AppOrderDetail detail = iAppOrderDetailService.getById(id);
        AppOrder order = orderService.getById(detail.getOrderId());
        GoodsMain goods = goodsMainService.getById(detail.getGoodsId());
        Integer fee = goods.getGoodsPrice().multiply(new BigDecimal(100)).intValue() * detail.getGoodsCount();

        String refundNo = System.currentTimeMillis() + RandomUtil.randomNumbers(6);
        WxPayRefundRequest request = new WxPayRefundRequest();
        request.setRefundFee(fee);
        request.setTotalFee(order.getTotalFee());
        request.setOutTradeNo(detail.getOrderId());
        request.setOutRefundNo(refundNo);
        WxPayRefundResult refund = null;
        try {
            wxPayService.refund(request);
            detail.setRefundTime(DateUtils.getNowDate());
            detail.setRefundNo(refundNo);
            detail.setStatus(3);
            iAppOrderDetailService.updateById(detail);
            return AjaxResult.success(detail);
        } catch (WxPayException e) {
            e.printStackTrace();
            return error();
        }
    }


    @PreAuthorize("@ss.hasPermi('winery:detail:query')")
    @Log(title = "请求退款", businessType = BusinessType.OTHER)
    @PostMapping("/requestRefund")
    AjaxResult requestRefund(@RequestBody AppRequestRefundDetailVo vo) {
        AppOrderDetail detail = iAppOrderDetailService.getById(vo.getId());
        detail.setStatus(1);
        detail.setRefundReason(vo.getRefundReason());
        iAppOrderDetailService.updateById(detail);
        return AjaxResult.success(detail);

    }
}
