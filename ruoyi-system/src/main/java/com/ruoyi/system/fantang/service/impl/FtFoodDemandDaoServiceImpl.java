package com.ruoyi.system.fantang.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtCateringDao;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.mapper.FtFoodDemandDaoMapper;
import com.ruoyi.system.fantang.service.IFtFoodDemandDaoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 病人报餐Service业务层处理
 *
 * @author ft
 * @date 2020-12-03
 */
@Service
public class FtFoodDemandDaoServiceImpl extends ServiceImpl<FtFoodDemandDaoMapper, FtFoodDemandDao> implements IFtFoodDemandDaoService {

    @Override
    public Integer GenerateOrderByPatientId(Long patientId) {
        return this.baseMapper.GenerateOrderByPatientId(patientId);
    }

    // 扫描所有未设置默认订餐信息的病人，增加配置信息
    @Override
    public Integer GenerateOrderForNewPatient() {
        // 获取所有未设置默认订餐需求病人
        List<Long> newPatients = this.baseMapper.getNewPatientNotDemand();
        for (Long patientId : newPatients) {
            // 先创建四个用餐信息
            this.baseMapper.GenerateOrderByPatientId(patientId);
            // 更新加餐信息为禁用状态
            this.baseMapper.updateExtraByPatientId(patientId);
        }
        return newPatients.size();
    }

    @Override
    public List<FtFoodDemandDao> listNewFormatter(FtFoodDemandDao ftFoodDemandDao) {
        return this.baseMapper.listNewFormatter(ftFoodDemandDao);
    }

    @Override
    public FtFoodDemandDao getByIdNewFormatter(Long id) {
        return this.baseMapper.getByIdNewFormatter(id);
    }

    @Override
    public List<FtOrderDao> getStatisticsOfDay(Date day) {
        return this.baseMapper.getStatisticsOfDate(DateUtil.beginOfDay(day).toString(), DateUtil.endOfDay(day).toString());
    }

    @Override
    public List<FtOrderDao> getStatisticsOfWeek(Date day) {
        return this.baseMapper.getStatisticsOfDate(DateUtil.beginOfWeek(day).toString(), DateUtil.endOfWeek(day).toString());
    }

    @Override
    public List<FtOrderDao> getStatisticsOfMonth(Date day) {
        return this.baseMapper.getStatisticsOfDate(DateUtil.beginOfMonth(day).toString(), DateUtil.endOfMonth(day).toString());
    }

    @Override
    public List<FtReportMealsDao> getStatisticsFoodDemand() {
        return this.baseMapper.getStatisticsFoodDemand();
    }

    @Override
    public Integer updateDayFoodDemand(List<FtCateringDao> ftCateringList) {

        int rows = 0;

        Long patientId = ftCateringList.get(0).getPatientId();
        QueryWrapper<FtFoodDemandDao> wrapper = new QueryWrapper<>();
        wrapper.eq("patient_id", patientId);
        wrapper.orderByAsc("type");
        List<FtFoodDemandDao> foodDemandList = this.baseMapper.selectList(wrapper);

        for (int i = 0; i < 4; i++) {
            FtFoodDemandDao foodDemand = foodDemandList.get(i);
            foodDemand.setNutritionFoodId(ftCateringList.get(i).getNumber());
            foodDemand.setNutritionFoodFlag(ftCateringList.get(i).getFlag());
            foodDemand.setOpenFlag(true);
            foodDemand.setUpdateAt(new Date());
            rows += this.baseMapper.updateById(foodDemand);
        }

        return rows;
    }

    @Override
    public Integer cancelNutritionByPatientId(Long[] ids) {

        int rows = 0;

        FtFoodDemandDao foodDemand = new FtFoodDemandDao();
        foodDemand.setNutritionFoodFlag(false);
        foodDemand.setUpdateAt(new Date());

        for (Long id : ids) {
            UpdateWrapper<FtFoodDemandDao> wrapper = new UpdateWrapper<>();
            wrapper.eq("patient_id", id);
            rows += this.baseMapper.update(foodDemand, wrapper);
        }

        return rows;
    }
}
