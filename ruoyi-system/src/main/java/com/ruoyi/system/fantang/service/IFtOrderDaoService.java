package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtOrderDao;

/**
 * 订单管理Service接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface IFtOrderDaoService extends IService<FtOrderDao> {

    AjaxResult getOrderOfToday(Long staffId);
}
