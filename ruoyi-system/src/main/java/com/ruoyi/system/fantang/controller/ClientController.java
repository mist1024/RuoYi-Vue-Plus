package com.ruoyi.system.fantang.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.common.DinnerTypeUtils;
import com.ruoyi.system.fantang.domain.*;
import com.ruoyi.system.fantang.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/client_api/staff")
public class ClientController extends BaseController {

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

    @Autowired
    private IFtFaceinfoDaoService faceinfoDaoService;

    @Autowired
    private IFtStaffSubsidyDaoService staffSubsidyDaoService;

    @Autowired
    private IFtFoodDaoService foodDaoService;

    @Autowired
    private IFtOrderDaoService iFtOrderDaoService;

    @Autowired
    private IFtFoodDefaultDaoService foodDefaultDaoService;

    @Autowired
    private IFtStaffStopMealsDaoService staffStopMealsDaoService;

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
     * @return AjaxResult
     */
    @GetMapping("/getOrderOfToday/{staffId}")
    public AjaxResult getOrderOfToday(@PathVariable("staffId") Long staffId) {
        return AjaxResult.success(orderDaoService.getOrderOfToday(staffId));
    }

    /**
     * 获取员工某一天的订单信息
     * 日期：2020年12月11日
     * 作者：陈智兴
     * <p>
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
     * <p>
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
     * <p>
     * param JSONObject staffId: 员工id
     * orderType：订餐类型
     * demandDate： 订餐用餐日期
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
     * <p>
     * param staffId: 员工id
     * type：订餐类型
     * demandDate： 订餐用餐日期
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
     * <p>
     * param staffId: 员工id
     * type：订餐类型
     * demandDate： 订餐用餐日期
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
     * <p>
     * param staffId: 员工id
     * type：订餐类型
     * demandDate： 订餐用餐日期
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
     * <p>
     * param { tel: 手机号码;
     * password： 密码
     * }
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
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(params.getDate("date"));
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekMenuDaoService.getMenuOfDay(weekDays[w]);
    }

    @GetMapping("/StatisGetOrderOfDate")
    public AjaxResult statisGetOrderOfDate(@RequestParam Date date) {
        return orderDaoService.statisGetOrderOfDate(date);
    }

    /**
     * 获取指定日期的订单明细
     * 类型，日期
     * 类型 = 0 ，所有
     * type = 1，2，3：早，午，晚
     */
    @PostMapping("/getOrderDetailedByDate")
    public AjaxResult getOrderDetailedByDate(@RequestBody JSONObject params) {

        String createAt = params.getString("createAt");
        Integer orderType = params.getInteger("orderType");
        String start = createAt + " 00:00:00";
        String end = createAt + " 23:59:59";

//        QueryWrapper<FtOrderDao> wrapper = new QueryWrapper<>();
//        if (orderType != 0) {
//            wrapper.eq("order_type", orderType);
//        }
//        wrapper.between("create_at", start, end);
//        orderDaoService.list(wrapper);

        List<FtOrderDao> orderList;

        if (orderType != 0) {
            orderList = orderDaoService.listDetailedByDate(orderType, start, end);
        } else {
            orderList = orderDaoService.listAllDetailedByDate(start, end);
        }

        return AjaxResult.success(orderList);
    }

    /**
     * 查看补贴记录
     */
    @GetMapping("/getStaffSubsidy/{staffId}")
    public AjaxResult getStaffSubsidy(@PathVariable Long staffId) {
        QueryWrapper<FtStaffSubsidyDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);

        return AjaxResult.success(staffSubsidyDaoService.list(wrapper));
    }

    /**
     * 查看补贴余额
     */
    @GetMapping("/getStaffSubsidyBalance/{staffId}")
    public AjaxResult getStaffSubsidyBalance(@PathVariable Long staffId) {
        FtStaffInfoDao staffInfoDao = staffInfoDaoService.getById(staffId);

        return AjaxResult.success(staffInfoDao.getBalance());
    }

    /**
     * 查看商品清单
     */
    @GetMapping("/getGoodsList")
    public AjaxResult getGoodsList() {
        QueryWrapper<FtFoodDao> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 2);
        List<FtFoodDao> list = foodDaoService.list(wrapper);

        return AjaxResult.success(list);
    }

    /**
     * 用餐流水（订单）查看
     */
    @GetMapping("/getStaffOrder/{staffId}")
    public AjaxResult getStaffOrder(@PathVariable Long staffId) {
        QueryWrapper<FtOrderDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);

        return AjaxResult.success(iFtOrderDaoService.list(wrapper));
    }

    /**
     * 获取截止订餐参数
     */
    @GetMapping("/getStopDinnerTime")
    public AjaxResult getStopDinnerTime() {
        QueryWrapper<FtConfigDao> wrapper = new QueryWrapper<>();
        wrapper.eq("config_key", "stop_dinner");
        FtConfigDao ftConfigDao = iFtConfigDaoService.getOne(wrapper);

        return AjaxResult.success(ftConfigDao.getConfigValue());
    }

    /**
     * 获取菜品清单
     */
    @GetMapping("/getFoodList")
    public AjaxResult getFoodList() {
        QueryWrapper<FtFoodDao> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 1);
        List<FtFoodDao> list = foodDaoService.list(wrapper);

        return AjaxResult.success(list);
    }

    /**
     * 获取各餐品的价格清单
     *
     * @param
     * @author : 陈智兴
     */
    @GetMapping("/getDinnerPriceList")
    public AjaxResult getDinnerPriceList() {
        List<FtFoodDefaultDao> list = foodDefaultDaoService.list(null);
        return AjaxResult.success(list);
    }


    /**
     * 获取订餐优惠比例
     */
    @GetMapping("/getOrderDiscount/{orderId}")
    public AjaxResult getOrderDiscount(@PathVariable Long orderId) {
        FtOrderDao ftOrderDao = orderDaoService.getById(orderId);

        return AjaxResult.success(ftOrderDao.getDiscount());
    }

    /**
     * 补贴订单收款
     */
    @Transactional
    @PostMapping("/orderCollection")
    public AjaxResult orderCollection(@RequestBody JSONObject params) {

        // 员工 id
        Long staffId = params.getLong("staffId");

        // 订餐类型
        Integer type = params.getInteger("type");

        // 总价
        BigDecimal totalPrice = params.getBigDecimal("receipts");

        // 生成新订单
        FtOrderDao orderDao = new FtOrderDao();
        orderDao.setOrderType(type);
        orderDao.setStaffId(staffId);
        orderDao.setTotalPrice(totalPrice);
        Date today = new Date();
        orderDao.setCreateAt(today);
        orderDao.setOrderSrc("在线订单");
        // 余额支付
        orderDao.setPayType(3);
        // 已支付
        orderDao.setPayFlag(1);
        // 未过期
        orderDao.setExpiredFlag(0);
        // 未核销
        orderDao.setWriteOffFlag(0);
        DateTime tomorrow = DateUtil.offsetDay(today, 1);
        orderDao.setOrderDate(tomorrow);

        // 当前订单员工信息
        FtStaffInfoDao staffInfoDao = staffInfoDaoService.getById(orderDao.getStaffId());

        // 员工账户补贴余额
        BigDecimal balance = staffInfoDao.getBalance();

        // 余额是否大于实收
        if (balance.compareTo(totalPrice) < 0) {

            return AjaxResult.error("补贴余额不足");

        } else {

            // 更新员工账户补贴余额
            BigDecimal nowBalance = balance.subtract(totalPrice);
            staffInfoDao.setBalance(nowBalance);

            orderDaoService.save(orderDao);

            // 添加补贴流水记录
            FtStaffSubsidyDao staffSubsidyDao = new FtStaffSubsidyDao();
            staffSubsidyDao.setStaffId(orderDao.getStaffId());
            staffSubsidyDao.setIncomeType(2);
            staffSubsidyDao.setPrice(totalPrice);
            staffSubsidyDao.setConsumAt(today);
            staffSubsidyDao.setOrderId(orderDao.getOrderId());
            staffSubsidyDaoService.save(staffSubsidyDao);

        }

        return AjaxResult.success(orderDao);
    }

    /**
     * 补贴订单退款
     */
    @Transactional
    @PostMapping("/orderRefund")
    public AjaxResult orderRefund(@RequestBody JSONObject params) {

        // 订单 id
        Integer orderId = params.getInteger("orderId");

        // 当前订单信息
        FtOrderDao orderDao = orderDaoService.getById(orderId);
        // 更新订单信息
        orderDao.setPayFlag(2);
        orderDaoService.save(orderDao);

        // 总价
        BigDecimal totalPrice = orderDao.getTotalPrice();

        // 当前订单员工信息
        FtStaffInfoDao staffInfoDao = staffInfoDaoService.getById(orderDao.getStaffId());

        // 员工账户补贴余额
        BigDecimal balance = staffInfoDao.getBalance();

        // 更新员工账户补贴余额
        staffInfoDao.setBalance(balance.add(totalPrice));
        staffInfoDaoService.updateById(staffInfoDao);

        // 添加补贴流水记录
        FtStaffSubsidyDao staffSubsidyDao = new FtStaffSubsidyDao();
        staffSubsidyDao.setStaffId(orderDao.getStaffId());
        // 退款
        staffSubsidyDao.setIncomeType(4);
        staffSubsidyDao.setPrice(totalPrice);
        staffSubsidyDao.setConsumAt(new Date());
        staffSubsidyDao.setOrderId(orderDao.getOrderId());
        staffSubsidyDaoService.save(staffSubsidyDao);

        return AjaxResult.success("已退款");
    }


    /**
     * 今日员工报餐统计
     */
    @GetMapping("/getStatisticsOrderOfToday")
    public AjaxResult getStatisticsOrderOfToday() {

        Date today = new Date();

        return orderDaoService.statisGetOrderOfDate(today);
    }

    /**
     * 明日员工报餐统计
     */
    @GetMapping("/getStatisticsOrderOfTomorrow")
    public AjaxResult getStatisticsReportMealsOfTomorrow() {

        Date today = new Date();
        Date tomorrow = DateUtil.offsetDay(today, 1);

        return orderDaoService.statisGetOrderOfDate(tomorrow);
    }


    /**
     * 人脸识别设备心跳信号
     *
     * @param request
     * @return
     */
    @PostMapping("/heartbeat")
    public String faceDeviceHeartbeatEvent(@RequestBody JSONObject request) {
        System.out.println("face device heartbeat.....");
        System.out.println(request);
        return "ok";

    }


    @PostMapping("/verify")
    public String faceDeviceVerifyEvent(@RequestBody JSONObject request) {

        // 判断是否在用餐时间，否则只写日志，不处理事件
        DinnerTypeUtils.DinnerType dinnerType = DinnerTypeUtils.getInstance(iFtConfigDaoService).getDinnerType();
        System.out.println(request);
        if (dinnerType == DinnerTypeUtils.DinnerType.notMatch) {
            log.info("data : {} ", request);
            return request.toJSONString();
        }

        // 从数据中获取人脸id
        JSONObject info = request.getJSONObject("info");
        Integer personId = info.getInteger("PersonID");
        Long deviceId = info.getLong("DeviceID");

        // 判断该设备是否有效
        Boolean isEffective =  iFtConfigDaoService.isDeviceEffective(deviceId);
        if (!isEffective) {
            log.info("设备有效性 : {} ", "该人脸识别设备已失效");
            return "该人脸识别设备已失效";
        }

        // 是否有该员工
        FtStaffInfoDao staffInfoDao = staffInfoDaoService.inStaff(personId);

        if (staffInfoDao != null) {

            // 获取员工 id
            Long staffId = staffInfoDao.getStaffId();

            String message;

            // 进行核销，如果该员工没有订餐则自动生成一个订餐记录并核销
            switch (dinnerType) {
                case breakfast:
                    message = orderDaoService.setWriteOff(staffId, 1, deviceId);
                    break;
                case lunch:
                    message = orderDaoService.setWriteOff(staffId, 2, deviceId);
                    break;
                case dinner:
                    message = orderDaoService.setWriteOff(staffId, 3, deviceId);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + dinnerType);
            }
            log.info("人脸核销结果 : {}", message);

            return message;

        } else {

            // 如果该人脸id没有对应的员工，则记录找日志中
            log.info("员工查询 : {}", "没有找到该员工");
            return "没有找到该员工";
        }

    }

    /**
     * 自动切换手动报餐时清空停餐记录
     */
    @DeleteMapping("deleteStopMeals/{staffId}")
    public AjaxResult deleteStopMeals(@PathVariable Long staffId) {

        QueryWrapper<FtStaffStopMealsDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        staffStopMealsDaoService.remove(wrapper);

        return AjaxResult.success("已删除");
    }

}
