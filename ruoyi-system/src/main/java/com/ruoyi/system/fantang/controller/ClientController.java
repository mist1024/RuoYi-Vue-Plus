package com.ruoyi.system.fantang.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.service.IFtConfigDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/client_api/staff")
public class ClientController extends BaseController {
    /**
     * 获取用餐时间
     */
    @Autowired
    private IFtConfigDaoService iFtConfigDaoService;

    @GetMapping("/getDinnerTimeSetting")
    public AjaxResult getDinnerTimeSetting() {
        return AjaxResult.success(iFtConfigDaoService.getDinnerTimeSetting());
    }

    @GetMapping("/getOrderOfToday/{staffId}")
    public AjaxResult getOrderOfToday(@PathVariable("staffId") Integer staffId) {
        return AjaxResult.success("调用成功");
    }

    @GetMapping("/getWeekMenu")
    public AjaxResult getWeekMenu(){
        return AjaxResult.success("调用每周菜谱成功");
    }

    @GetMapping("/getAvailableOrder/{staffId}")
    public AjaxResult getAvailableOrder(@PathVariable("staffId") Integer staffId) {
        return AjaxResult.success("调用有效订单成功");
    }

    @PostMapping("/PostOrder")
    public AjaxResult postOrder() {
        return AjaxResult.success("调用提交订单成功");
    }

    @GetMapping("/getConfiguration/{staffId}")
    public AjaxResult getConfiguration(@PathVariable("staffId") Integer staffId) {
        return AjaxResult.success("调用个人配置");
    }

    @PostMapping("/postConfiguration")
    public AjaxResult postConfiguration() {
        return AjaxResult.success("推送个人配置");
    }

    @PostMapping("/login")
    public AjaxResult login() {
        return AjaxResult.success("登录成功");
    }
    @PostMapping("/logout")
    public AjaxResult logout() {
        return AjaxResult.success("登录成功");
    }

    @GetMapping("/getWorkday")
    public AjaxResult getWorkday() {
        return null;
    }

    @GetMapping("/getProduct")
    public AjaxResult getProduct() {
        return null;
    }

    @GetMapping("/getOtherProduct")
    public AjaxResult getOtherProduct() {
        return null;
    }
    @GetMapping("/postProductOrder")
    public AjaxResult postProductOrder() {
        return null;
    }
    @GetMapping("/postCurrentOrder")
    public AjaxResult postCurrentOrder() {
        return null;
    }
    @GetMapping("/postStopOrder")
    public AjaxResult postStopOrder() {
        return null;
    }
    @GetMapping("/postRestoreOrder")
    public AjaxResult postRestoreOrder() {
        return null;
    }
}
