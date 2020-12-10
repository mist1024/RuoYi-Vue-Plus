package com.ruoyi.system.fantang.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtStaffInfoDao;
import com.ruoyi.system.fantang.service.IFtConfigDaoService;
import com.ruoyi.system.fantang.service.IFtStaffDemandDaoService;
import com.ruoyi.system.fantang.service.IFtStaffInfoDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/client_api/staff")
public class ClientController extends BaseController {
    /**
     * 获取用餐时间
     */
    @Autowired
    private IFtConfigDaoService iFtConfigDaoService;

    @Autowired
    private IFtStaffInfoDaoService staffInfoDaoService;

    @Autowired
    private IFtStaffDemandDaoService staffDemandDaoService;

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

    // 获取配置信息
    @GetMapping("/getConfiguration/{staffId}")
    public AjaxResult getConfiguration(@PathVariable("staffId") Integer staffId) {
        staffDemandDaoService.getConfiguration(staffId);
        return AjaxResult.success("调用个人配置");
    }

    // 获取配置信息
    @PostMapping("/postConfiguration/{staffId}")
    public AjaxResult postConfiguration(@PathVariable("staffId") Long staffId) {

        return AjaxResult.success("推送个人配置");
    }

    @GetMapping("/login")
    public AjaxResult login(String tel, String password) {
        return staffInfoDaoService.login(tel, password);
    }
    @PostMapping("/logout/{staffId}")
    public AjaxResult logout(@PathVariable("staffId") Long staffId) {
        staffInfoDaoService.logout(staffId);
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
    @PostMapping("/postProductOrder")
    public AjaxResult postProductOrder() {
        return null;
    }
    @PostMapping("/postCurrentOrder")
    public AjaxResult postCurrentOrder() {
        return null;
    }
    @PostMapping("/postStopOrder")
    public AjaxResult postStopOrder() {
        return null;
    }
    @PostMapping("/postRestoreOrder")
    public AjaxResult postRestoreOrder() {
        return null;
    }

    @PostMapping("/setDemandMode")
    public AjaxResult setDemandMode(@RequestBody JSONObject params) {
        Long id = params.getLong("id");
        Boolean demandMode = params.getBoolean("demandModeFlag");
        return staffDemandDaoService.setDemandMode(id, demandMode);
    }
}
