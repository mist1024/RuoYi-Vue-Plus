package com.ruoyi.winery.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.itextpdf.io.util.DateTimeUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.winery.domain.AppOrder;
import com.ruoyi.winery.domain.AppOrderDetail;
import com.ruoyi.winery.domain.AppUserAddress;
import com.ruoyi.winery.domain.goods.GoodsMain;
import com.ruoyi.winery.domain.winery.WineryOrders;
import com.ruoyi.winery.service.IAppOrderDetailService;
import com.ruoyi.winery.service.IAppOrderService;
import com.ruoyi.winery.service.IAppUserAddressService;
import com.ruoyi.winery.service.IGoodsMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.core.domain.AjaxResult.error;
import static com.ruoyi.common.core.domain.AjaxResult.success;
import static com.ruoyi.common.utils.SecurityUtils.*;

/**
 * 订单Controller
 *
 * @author ruoyi
 * @date 2021-01-18
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/winery/order")
public class AppOrderController extends BaseController {

    private final IAppOrderService iAppOrderService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private IGoodsMainService goodsMainService;

    @Autowired
    private IAppOrderDetailService detailService;

    @Autowired
    private IAppUserAddressService addressService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('winery:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppOrder appOrder) {
        startPage();
        LambdaQueryWrapper<AppOrder> lqw = Wrappers.lambdaQuery(appOrder);
        if (appOrder.getUserId() != null) {
            lqw.eq(AppOrder::getUserId, appOrder.getUserId());
        }
        if (StringUtils.isNotBlank(appOrder.getPostName())) {
            lqw.eq(AppOrder::getPostName, appOrder.getPostName());
        }
        if (StringUtils.isNotBlank(appOrder.getPostMobile())) {
            lqw.eq(AppOrder::getPostName, appOrder.getPostName());
        }
        if (StringUtils.isNotBlank(appOrder.getPostRegion())) {
            lqw.eq(AppOrder::getPostName, appOrder.getPostName());
        }
        if (StringUtils.isNotBlank(appOrder.getPostAddress())) {
            lqw.eq(AppOrder::getPostName, appOrder.getPostName());
        }
        if (StringUtils.isNotBlank(appOrder.getPayMsg())) {
            lqw.eq(AppOrder::getPayMsg, appOrder.getPayMsg());
        }
        if (appOrder.getTotalFee() != null) {
            lqw.eq(AppOrder::getTotalFee, appOrder.getTotalFee());
        }
        if (StringUtils.isNotBlank(appOrder.getTransportNo())) {
            lqw.eq(AppOrder::getTransportNo, appOrder.getTransportNo());
        }
        if (appOrder.getStatus() != null) {
            lqw.eq(AppOrder::getStatus, appOrder.getStatus());
        }
        if (appOrder.getPayTime() != null) {
            lqw.eq(AppOrder::getPayTime, appOrder.getPayTime());
        }
        if (appOrder.getCancelTime() != null) {
            lqw.eq(AppOrder::getCancelTime, appOrder.getCancelTime());
        }
        lqw.orderByDesc(AppOrder::getCreateTime);

        if (isMiniUser()) {
            lqw.eq(AppOrder::getUserId, getLoginUser().getUser().getUserId());
        }

        List<AppOrder> list = iAppOrderService.list(lqw);
        for (AppOrder order : list) {
            LambdaQueryWrapper<AppOrderDetail> wrapper = new LambdaQueryWrapper<AppOrderDetail>();
            wrapper.eq(AppOrderDetail::getOrderId, order.getId());
            List<AppOrderDetail> detailList = detailService.list(wrapper);
            for (AppOrderDetail detail : detailList) {
                detail.setGoods(goodsMainService.getById(detail.getGoodsId()));
            }
            order.setOrderDetailList(detailList);
        }
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('winery:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AppOrder appOrder) {
        LambdaQueryWrapper<AppOrder> lqw = new LambdaQueryWrapper<AppOrder>(appOrder);
        List<AppOrder> list = iAppOrderService.list(lqw);
        ExcelUtil<AppOrder> util = new ExcelUtil<AppOrder>(AppOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        AppOrder order = iAppOrderService.getById(id);
        LambdaQueryWrapper<AppOrderDetail> wrapper = new LambdaQueryWrapper<AppOrderDetail>();
        wrapper.eq(AppOrderDetail::getOrderId, order.getId());
        List<AppOrderDetail> detailList = detailService.list(wrapper);
        order.setOrderDetailList(detailList);
        for (AppOrderDetail detail : detailList) {
            detail.setGoods(goodsMainService.getById(detail.getGoodsId()));
        }
        return success(order);
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('winery:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody AppOrder appOrder, HttpServletRequest req) {
        Long userId = getLoginUser().getUser().getUserId();
        String username = getUsername();

        String id = System.currentTimeMillis() + RandomUtil.randomNumbers(6);


        AppUserAddress address = addressService.getById(appOrder.getAddressId());

        if (address == null) {
            return AjaxResult.error("请校验地址信息");
        }

        appOrder.setPostMobile(address.getMobile());
        appOrder.setPostName(address.getName());
        appOrder.setPostRegion(address.getRegion());
        appOrder.setPostAddress(address.getAddress());

        appOrder.setId(id);
        appOrder.setUserId(userId);

        // 计算总金额
        List<AppOrderDetail> orderDetailList = appOrder.getOrderDetailList();
        Integer totalFee = 0;
        for (AppOrderDetail detail : orderDetailList) {
            GoodsMain goods = goodsMainService.getById(detail.getGoodsId());
            detail.setUserId(userId);
            detail.setOrderId(id);
            // 使用产品对应酒庄id
            detail.setDeptId(goods.getDeptId());
            detail.setStatus(0);
            detailService.save(detail);
            totalFee += (goods.getGoodsPrice().multiply(new BigDecimal(100)).intValue() * detail.getGoodsCount());
        }
        appOrder.setTotalFee(totalFee);

        // 统一下单
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        String openId = "";
        if (username.contains("mini-")) {
            openId = username.split("-")[1];
        }
        request.setOpenid(openId);
        request.setTotalFee(totalFee);
        request.setBody("订单编号" + id);
        request.setOutTradeNo(id);
        request.setSpbillCreateIp(req.getRemoteAddr());
        request.setTradeType("JSAPI");

        try {
            WxPayMpOrderResult payMsg = wxPayService.createOrder(request);
            appOrder.setPayMsg(((JSONObject) JSONObject.toJSON(payMsg)).toJSONString());
            appOrder.setStatus(0);
            iAppOrderService.save(appOrder);

            return success("success", payMsg);
        } catch (WxPayException e) {
            e.printStackTrace();
            return error();
        }
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('winery:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppOrder appOrder) {

        // 0=待支付,1=已取消,2=已支付,3=待收货,4=交易完成
        if (appOrder.getStatus() == 1) {
            appOrder.setCancelTime(DateUtils.getNowDate());
        }

        return toAjax(iAppOrderService.updateById(appOrder) ? 1 : 0);
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('winery:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(iAppOrderService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }

    @Log(title = "回调", businessType = BusinessType.OTHER)
    @PostMapping("/pay/payNotify")
    String payNotify(@RequestBody String xmlData) throws WxPayException {
        WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
        AppOrder order = iAppOrderService.getById(notifyResult.getOutTradeNo());
        order.setTransitionId(notifyResult.getTransactionId());
        order.setStatus(2);
        order.setPayTime(DateUtils.getNowDate());
        iAppOrderService.updateById(order);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }



}
