package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtOrderDao;

import java.util.Date;

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
}
