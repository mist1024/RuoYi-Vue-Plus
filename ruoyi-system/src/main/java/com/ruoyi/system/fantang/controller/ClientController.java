package com.ruoyi.system.fantang.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.service.IFtConfigDaoService;
import com.ruoyi.system.fantang.service.IFtOrderDaoService;
import com.ruoyi.system.fantang.service.IFtStaffDemandDaoService;
import com.ruoyi.system.fantang.service.IFtStaffInfoDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


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

    @Autowired
    private IFtOrderDaoService orderDaoService;

    /**
     * 获取用餐时间信息
     * 日期：2020年12月11日
     * 作者：陈智兴
     * type：订餐类型
     *
     * @return
     */
    @GetMapping("/getDinnerTimeSetting")
    public AjaxResult getDinnerTimeSetting() {
        return AjaxResult.success(iFtConfigDaoService.getDinnerTimeSetting());
    }

    /**
     * 获取员工当天订单信息
     * 日期：2020年12月11日
     * 作者：陈智兴
     *
     * @return
     */
    @GetMapping("/getOrderOfToday/{staffId}")
    public AjaxResult getOrderOfToday(@PathVariable("staffId") Long staffId) {
        return AjaxResult.success(orderDaoService.getOrderOfToday(staffId));
    }

    @GetMapping("/getWeekMenu")
    public AjaxResult getWeekMenu() {
        return AjaxResult.success("调用每周菜谱成功");
    }

    @GetMapping("/getAvailableOrder/{staffId}")
    public AjaxResult getAvailableOrder(@PathVariable("staffId") Integer staffId) {
        return AjaxResult.success(orderDaoService.getAvailableOrder(staffId));
    }

    /**
     * 推送订单信息
     * 日期：2020年12月11日
     * 作者：陈智兴
     *
     * @param JSONObject staffId: 员工id
     *                   orderType：订餐类型
     *                   demandDate： 订餐用餐日期
     * @return
     */
    @PostMapping("/PostOrder")
    public AjaxResult postOrder(@RequestBody JSONObject params) {
            orderDaoService.insertOrder(params.getLong("staffId"), params.getInteger("orderType"), params.getDate("demandDate"));

        return AjaxResult.success("调用提交订单成功");
    }

    // 获取配置信息
    @GetMapping("/getConfiguration/{staffId}")
    public AjaxResult getConfiguration(@PathVariable("staffId") Long staffId) {
        return AjaxResult.success(staffDemandDaoService.getConfiguration(staffId));
    }

    // 推送配置信息
    @PostMapping("/postConfiguration/{staffId}")
    public AjaxResult postConfiguration(@PathVariable("staffId") Long staffId) {

        return AjaxResult.success("推送个人配置");
    }

    /**
     * 设置订餐模式
     * 日期：2020年12月10日
     * 作者： 陈智兴
     * 修改：首次创建
     *
     * @param { tel: 手机号码;
     *          password： 密码
     *          }
     * @return 返回员工信息
     */
    @GetMapping("/login")
    public AjaxResult login(String tel, String password) {
        return staffInfoDaoService.login(tel, password);
    }

    @PostMapping("/logout/{staffId}")
    public AjaxResult logout(@PathVariable("staffId") Long staffId) {
        return AjaxResult.success(staffInfoDaoService.logout(staffId));
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

    /**
     * 推送停餐信息
     * 日期：2020年12月11日
     * 作者：陈智兴
     *
     * @param staffId: 员工id
     *                 type：订餐类型
     *                 demandDate： 订餐用餐日期
     * @return
     */
    @PostMapping("/postStopOrder")
    public AjaxResult postStopOrder(@RequestBody JSONObject params) {
        return null;
    }

    @PostMapping("/postRestoreOrder")
    public AjaxResult postRestoreOrder() {
        return null;
    }

    /**
     * 设置订餐模式
     * 日期：2020年12月11日
     * 作者： 陈智兴
     * 修改：首次创建
     *
     * @param { config_id: id;
     *          demandMode: true:自动模式；false:手动模式
     *          }
     * @return
     */
    @PostMapping("/setDemandMode")
    public AjaxResult setDemandMode(@RequestBody JSONObject params) {
        Long id = params.getLong("id");
        Boolean demandMode = params.getBoolean("demandModeFlag");
        return staffDemandDaoService.setDemandMode(id, demandMode);
    }
}
