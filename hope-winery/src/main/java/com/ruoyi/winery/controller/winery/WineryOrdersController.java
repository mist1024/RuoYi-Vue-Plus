package com.ruoyi.winery.controller.winery;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.winery.domain.winery.WineryOrders;
import com.ruoyi.winery.service.IWineryOrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

import static com.ruoyi.common.core.domain.AjaxResult.error;
import static com.ruoyi.common.core.domain.AjaxResult.success;
import static com.ruoyi.common.utils.SecurityUtils.*;

/**
 * 客户订单Controller
 *
 * @author ruoyi
 * @date 2020-12-28
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/winery/user_orders")
public class WineryOrdersController extends BaseController {

    private final IWineryOrdersService iWineryOrdersService;

    @Autowired
    private WxPayService wxPayService;

    /**
     * 查询客户订单列表
     */
    @PreAuthorize("@ss.hasPermi('winery:user_orders:list')")
    @GetMapping("/list")
    public TableDataInfo list(WineryOrders wineryOrders) {
        startPage();
        LambdaQueryWrapper<WineryOrders> lqw = Wrappers.lambdaQuery(wineryOrders);

        lqw.eq(!isAdmin(), WineryOrders::getDeptId, getDeptId());

        if (wineryOrders.getGoodsId() != null) {
            lqw.eq(WineryOrders::getGoodsId, wineryOrders.getGoodsId());
        }
        if (StringUtils.isNotBlank(wineryOrders.getGoodsName())) {
            lqw.like(WineryOrders::getGoodsName, wineryOrders.getGoodsName());
        }
        if (StringUtils.isNotBlank(wineryOrders.getGoodsType())) {
            lqw.eq(WineryOrders::getGoodsType, wineryOrders.getGoodsType());
        }
        if (StringUtils.isNotBlank(wineryOrders.getGoodsSpec())) {
            lqw.eq(WineryOrders::getGoodsSpec, wineryOrders.getGoodsSpec());
        }
        if (StringUtils.isNotBlank(wineryOrders.getGoodsFaceImg())) {
            lqw.eq(WineryOrders::getGoodsFaceImg, wineryOrders.getGoodsFaceImg());
        }
        if (wineryOrders.getGoodsPrice() != null) {
            lqw.eq(WineryOrders::getGoodsPrice, wineryOrders.getGoodsPrice());
        }
        if (wineryOrders.getGoodsCount() != null) {
            lqw.eq(WineryOrders::getGoodsCount, wineryOrders.getGoodsCount());
        }
        if (wineryOrders.getOrderStatus() != null) {
            lqw.eq(WineryOrders::getOrderStatus, wineryOrders.getOrderStatus());
        }
        List<WineryOrders> list = iWineryOrdersService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出客户订单列表
     */
    @PreAuthorize("@ss.hasPermi('winery:user_orders:export')")
    @Log(title = "客户订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WineryOrders wineryOrders) {
        LambdaQueryWrapper<WineryOrders> lqw = new LambdaQueryWrapper<WineryOrders>(wineryOrders);
        List<WineryOrders> list = iWineryOrdersService.list(lqw);
        ExcelUtil<WineryOrders> util = new ExcelUtil<WineryOrders>(WineryOrders.class);
        return util.exportExcel(list, "user_orders");
    }

    /**
     * 获取客户订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:user_orders:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iWineryOrdersService.getById(id));
    }

    /**
     * 新增客户订单
     */
    @PreAuthorize("@ss.hasPermi('winery:user_orders:add')")
    @Log(title = "客户订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WineryOrders wineryOrders, HttpServletRequest req) {
        wineryOrders.setCreateBy(getUsername());
        wineryOrders.setDeptId(getDeptId());

        // 统一下单
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setTotalFee(wineryOrders.getGoodsPrice().multiply(new BigDecimal(100)).intValue());
        request.setBody(wineryOrders.getGoodsName());
        String outTradeNo = UUID.randomUUID().toString().replace("-", "");
        request.setOutTradeNo(outTradeNo);
        request.setNotifyUrl("");
        request.setTradeType("JSAPI");
        String userName = getLoginUser().getUser().getUserName();
        String openId = "";
        if (userName.contains("mini-")) {
            openId = userName.split(",")[1];
        }
        request.setOpenid(openId);

        Map<String, Object> map = new HashMap<>();
        try {
            map.put("orderId", wineryOrders.getId());
            Objects payMsg = wxPayService.createOrder(request);
            map.put("payMsg", payMsg);
            wineryOrders.setPayMsg(payMsg.toString());
            iWineryOrdersService.save(wineryOrders);
            return success("success", map);
        } catch (WxPayException e) {
            e.printStackTrace();
            return error();
        }
    }

    /**
     * 修改客户订单
     */
    @PreAuthorize("@ss.hasPermi('winery:user_orders:edit')")
    @Log(title = "客户订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WineryOrders wineryOrders) {
        wineryOrders.setUpdateBy(getUsername());
        return toAjax(iWineryOrdersService.updateById(wineryOrders) ? 1 : 0);
    }

    /**
     * 删除客户订单
     */
    @PreAuthorize("@ss.hasPermi('winery:user_orders:remove')")
    @Log(title = "客户订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iWineryOrdersService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }

    @Log(title = "回调", businessType = BusinessType.OTHER)
    @PostMapping("/payNotify")
    String payNotify(@RequestBody String xmlData) throws WxPayException {
        WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

    @PreAuthorize("@ss.hasPermi('winery:user_orders:remove')")
    @Log(title = "退款", businessType = BusinessType.OTHER)
    @PostMapping("/refund")
    AjaxResult refund(@PathVariable Long id) {
        WineryOrders order = iWineryOrdersService.getById(id);
        WxPayRefundRequest request = new WxPayRefundRequest();
        request.setRefundFee(order.getGoodsPrice().intValue());
        request.setTotalFee(order.getGoodsPrice().intValue());
        request.setOutTradeNo(order.getOutTradeNo());
        WxPayRefundResult refund = null;
        try {
            refund = wxPayService.refund(request);
            return AjaxResult.success(refund);
        } catch (WxPayException e) {
            e.printStackTrace();
            return error();
        }
    }
}
