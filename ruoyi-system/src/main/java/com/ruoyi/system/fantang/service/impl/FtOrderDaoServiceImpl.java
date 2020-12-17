package com.ruoyi.system.fantang.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.mapper.FtOrderDaoMapper;
import com.ruoyi.system.fantang.service.IFtOrderDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单管理Service业务层处理
 *
 * @author ft
 * @date 2020-11-19
 */
@Service
public class FtOrderDaoServiceImpl extends ServiceImpl<FtOrderDaoMapper, FtOrderDao> implements IFtOrderDaoService {

    public void GenerateStaffTomorrowOrder() {
        this.baseMapper.GenerateStaffTomorrowOrder();

    }

    @Override
    public AjaxResult getOrderOfToday(Long staffId) {

        QueryWrapper<FtOrderDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        wrapper.between("order_date", DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()));
        List<FtOrderDao> daos = this.baseMapper.selectList(wrapper);
        return AjaxResult.success(daos);
    }

    @Override
    public Integer insertOrder(Long staffId, Integer orderType, Date demandDate) {
        FtOrderDao dao = new FtOrderDao();
        dao.setStaffId(staffId);
        dao.setOrderType(orderType);
        dao.setOrderDate(demandDate);
        return this.baseMapper.insert(dao);
    }

    @Override
    public AjaxResult getAvailableOrder(Integer staffId) {
        QueryWrapper<FtOrderDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        List<FtOrderDao> daos = this.baseMapper.selectList(wrapper);
        return AjaxResult.success(daos);
    }
}
