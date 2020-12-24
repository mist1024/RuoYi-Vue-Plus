package com.ruoyi.system.fantang.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
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

    @Autowired
    private IFtWeekMenuDaoService weekMenuDaoService;

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

    /**
     * 获取员工某一天的订单信息
     * 日期：2020年12月11日
     * 作者：陈智兴
     *
     * return
     */
    @GetMapping("/getOrderOfDay")
    public AjaxResult getOrderOfDate(@RequestParam("staffId") Long staffId, @RequestParam("orderDate") Date orderDate) {
        return AjaxResult.success(orderDaoService.getOrderOfDay(staffId, orderDate));
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
     * 获取员工停餐信息
     * 日期：2020年12月21日
     * 作者：陈智兴
     *
     * param JSONObject staffId: 员工id
     * return
     */
    @PostMapping("/getAvailableStopOrder")
    public AjaxResult getAvailableStopOrder(@RequestBody JSONObject params) {
        return AjaxResult.success(orderDaoService.getAvailableStopOrder(params.getLong("staffId")));
    }


    /**
     * 推送订单信息
     * 日期：2020年12月11日
     * 作者：陈智兴
     *
     * param JSONObject staffId: 员工id
     *                   orderType：订餐类型
     *                   demandDate： 订餐用餐日期
     * return
     */
    @PostMapping("/PostOrder")
    public AjaxResult postOrder(@RequestBody JSONObject params) {
            return AjaxResult.success(orderDaoService.insertOrder(params.getLong("staffId"), params.getInteger("orderType"), params.getDate("demandDate")));
    }

    /**
     * 推送停餐信息
     * 日期：2020年12月21日
     * 作者：陈智兴
     *
     * param staffId: 员工id
     *                 type：订餐类型
     *                 demandDate： 订餐用餐日期
     * return -1: 已报停餐信息， 1： 停餐成功
     */
    @PostMapping("/postStopOrder")
    public AjaxResult postStopOrder(@RequestBody JSONObject params) {
        return AjaxResult.success(orderDaoService.stopOrder(params.getLong("staffId"), params.getInteger("orderType"), params.getDate("demandDate")));
    }

    /**
     * 员工取消订餐信息
     * 日期：2020年12月21日
     * 作者：陈智兴
     *
     * param staffId: 员工id
     *                 type：订餐类型
     *                 demandDate： 订餐用餐日期
     * return -1: 已报停餐信息， 1： 停餐成功
     */
    @PostMapping("/postCancelOrder")
    public AjaxResult postCancelOrder(@RequestBody JSONObject params) {
        return orderDaoService.cancelOrder(params.getLong("orderId"));
    }

    /**
     * 推送取消停餐信息
     * 日期：2020年12月21日
     * 作者：陈智兴
     *
     * param staffId: 员工id
     *                 type：订餐类型
     *                 demandDate： 订餐用餐日期
     * return -1: 已报停餐信息， 1： 停餐成功
     */
    @PostMapping("/postCancelStopOrder")
    public AjaxResult postCancelStopOrder(@RequestBody JSONObject params) {
        return AjaxResult.success(orderDaoService.cancelStopOrder(params.getLong("orderId")));
    }

    // 获取配置信息
    @GetMapping("/getConfiguration/{staffId}")
    public AjaxResult getConfiguration(@PathVariable("staffId") Long staffId) {
        return AjaxResult.success(staffDemandDaoService.getConfiguration(staffId));
    }

    /**
     * 员工登录
     * 日期：2020年12月10日
     * 作者： 陈智兴
     * 修改：首次创建
     *
     * param { tel: 手机号码;
     *          password： 密码
     *          }
     * return 返回员工信息
     */
    @GetMapping("/login")
    public AjaxResult login(String tel, String password) {
        return staffInfoDaoService.login(tel, password);
    }

    @PostMapping("/logout")
    public AjaxResult logout(@RequestBody JSONObject params) {
        return AjaxResult.success(staffInfoDaoService.logout(params.getLong("staffId")));
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

    @PostMapping("/postRestoreOrder")
    public AjaxResult postRestoreOrder() {
        return null;
    }

    /**
     * 初始化员工订餐配置文件，并返回初始化后的记录给前端
     * params: staffId
     * return
     */
    @PostMapping("/initDemandMode")
    public AjaxResult initDemandMode(@RequestBody JSONObject params) {
        return staffDemandDaoService.initDemandMode(params.getLong("staffId"));
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
        Integer type = params.getInteger("type");
        Boolean demandMode = params.getBoolean("demandModeFlag");
        return staffDemandDaoService.setDemandMode(id, type, demandMode);
    }

    /**
     * 返回某天的菜单
     * param  today
     * return
     */
    @PostMapping("/getMenuOfDay")
    public AjaxResult getMenuOfDay(@RequestBody JSONObject params) {
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(params.getDate("date"));
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekMenuDaoService.getMenuOfDay(weekDays[w]);
    }

    @GetMapping("/StatisGetOrderOfDate")
    public AjaxResult statisGetOrderOfDate(@RequestParam Date date) {
        return orderDaoService.statisGetOrderOfDate(date);
    }
}
