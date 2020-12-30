package com.ruoyi.system.fantang.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.domain.FtStaffStopMealsDao;
import com.ruoyi.system.fantang.mapper.FtOrderDaoMapper;
import com.ruoyi.system.fantang.mapper.FtStaffStopMealsDaoMapper;
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

    @Autowired
    FtStaffStopMealsDaoMapper staffStopMealsDaoMapper;

    public void GenerateStaffTomorrowOrder() {
        int week = DateUtil.thisDayOfWeek();
        if (week < 6)
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
        // 先删除停餐记录，再添加订餐记录
        QueryWrapper<FtStaffStopMealsDao> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("staff_id", staffId);
        queryWrapper.eq("type", orderType);
        queryWrapper.between("demand_date", DateUtil.beginOfDay(demandDate), DateUtil.endOfDay(demandDate));
        staffStopMealsDaoMapper.delete(queryWrapper);

        // 查找是否已经订餐
        FtOrderDao dao = new FtOrderDao();
        dao.setStaffId(staffId);
        dao.setOrderType(orderType);
        dao.setOrderDate(demandDate);
        QueryWrapper<FtOrderDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        wrapper.eq("order_type", orderType);
        wrapper.between("order_date", DateUtil.beginOfDay(demandDate), DateUtil.endOfDay(demandDate));
        Integer count = this.baseMapper.selectCount(wrapper);
        if (count > 0)
            return -1;
        else
            return this.baseMapper.insert(dao);
    }

    @Override
    public AjaxResult getAvailableOrder(Integer staffId) {
        QueryWrapper<FtOrderDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        wrapper.ge("order_date", DateUtil.beginOfDay(new Date()));
        List<FtOrderDao> daos = this.baseMapper.selectList(wrapper);
        return AjaxResult.success(daos);
    }

    @Override
    public AjaxResult getOrderOfDay(Long staffId, Date orderDate) {
        QueryWrapper<FtOrderDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        wrapper.between("order_date", DateUtil.beginOfDay(orderDate), DateUtil.endOfDay(orderDate));
        List<FtOrderDao> daos = this.baseMapper.selectList(wrapper);
        return AjaxResult.success(daos);
    }

    @Override
    public AjaxResult stopOrder(Long staffId, Integer orderType, Date demandDate) {
        // 先删除当天的订餐记录，再添加停餐记录
        QueryWrapper<FtOrderDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        wrapper.eq("order_type", orderType);
        wrapper.between("order_date", DateUtil.beginOfDay(demandDate), DateUtil.endOfDay(demandDate));
        this.baseMapper.delete(wrapper);

        // 添加停餐记录
        FtStaffStopMealsDao dao = new FtStaffStopMealsDao();
        dao.setStaffId(staffId);
        dao.setType(orderType);
        dao.setDemandDate(demandDate);
        dao.setCreateAt(new Date());
        return AjaxResult.success(staffStopMealsDaoMapper.insert(dao));
    }

    @Override
    public AjaxResult cancelOrder(Long orderId) {
        FtOrderDao dao = new FtOrderDao();
        dao.setOrderId(orderId);
        return AjaxResult.success(this.baseMapper.deleteById(dao));
    }

    @Override
    public AjaxResult getAvailableStopOrder(Long staffId) {
        QueryWrapper<FtStaffStopMealsDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        wrapper.ge("demand_date", new Date());
        return AjaxResult.success(staffStopMealsDaoMapper.selectList(wrapper));
    }

    @Override
    public AjaxResult cancelStopOrder(Long orderId) {
        return AjaxResult.success(staffStopMealsDaoMapper.deleteById(orderId));
    }

    @Override
    public AjaxResult statisGetOrderOfDate(Date date) {
        return AjaxResult.success(this.baseMapper.statisGetOrderOfDate(DateUtil.beginOfDay(date).toString(), DateUtil.endOfDay(date).toString()));
    }

    @Override
    public List<FtOrderDao> listDetailedByDate(Integer orderType, String start, String end) {
        return this.baseMapper.listDetailedByDate(orderType, start, end);
    }

    @Override
    public List<FtOrderDao> listAllDetailedByDate(String start, String end) {
        return this.baseMapper.listAllDetailedByDate(start, end);
    }
}
