package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.domain.FtStaffDemandDao;
import com.ruoyi.system.fantang.domain.FtStaffStopMealsDao;

import java.util.Date;
import java.util.List;

/**
 * 订单管理Service接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface IFtOrderDaoService extends IService<FtOrderDao> {

    AjaxResult getOrderOfToday(Long staffId);

    Integer insertOrder(Long staffId, Integer orderType, Date demandDate);

    AjaxResult getAvailableOrder(Integer staffId);

    AjaxResult getOrderOfDay(Long staffId, Date orderDate);

    AjaxResult stopOrder(Long staffId, Integer orderType, Date demandDate);

    AjaxResult cancelOrder(Long orderId);

    AjaxResult getAvailableStopOrder(Long staffId);

    AjaxResult cancelStopOrder(Long orderId);

    IPage<FtOrderDao> statisGetOrderOfDate(Date date, Integer pageNum, Integer pageSize);

    IPage<FtOrderDao> statisGetOrderOfDateByPerson(Date date, Integer pageNum, Integer pageSize);

    List<FtOrderDao> statisOrderOfDateByPersonNoPage(Date date);

    IPage<FtOrderDao> statisGetOrderOfWeek(Date date, Integer pageNum, Integer pageSize);

    IPage<FtOrderDao> statisGetOrderOfWeekByPerson(Date date, Integer pageNum, Integer pageSize);

    IPage<FtOrderDao> statisGetOrderOfMonth(Date date, Integer pageNum, Integer pageSize);

    IPage<FtOrderDao> statisGetOrderOfMonthByPerson(Date date, Integer pageNum, Integer pageSize);

    List<FtOrderDao> listDetailedByDate(Integer orderType, String start, String end);

    List<FtOrderDao> listAllDetailedByDate(String start, String end);

    String setWriteOff(Long staffId, int type, Long deviceId);

    List<FtFoodDemandDao> getStatisticsReportMealsOfTomorrow();

    List<FtStaffDemandDao> getStatisticsStaffOfTomorrow();

    List<FtStaffStopMealsDao> getStopOrderOfTomorrow();

    List<FtOrderDao> statisOrderOfWeekByPersonNoPage(Date selectWeek);

    List<FtOrderDao> statisOrderOfMonthByPersonNoPage(Date selectMonth);
}
