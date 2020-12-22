package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.entity.ReportMealsDayEntity;
import com.ruoyi.system.fantang.entity.ReportMealsPriceEntity;
import com.ruoyi.system.fantang.mapper.FtReportMealVoMapper;
import com.ruoyi.system.fantang.mapper.FtReportMealsDaoMapper;
import com.ruoyi.system.fantang.service.IFtReportMealsDaoService;
import com.ruoyi.system.fantang.vo.FtReportMealVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 报餐管理Service业务层处理
 *
 * @author ft
 * @date 2020-11-19
 */
@Service
public class FtReportMealsDaoServiceImpl extends ServiceImpl<FtReportMealsDaoMapper, FtReportMealsDao> implements IFtReportMealsDaoService {

    @Autowired
    private FtReportMealVoMapper ftReportMealVoMapper;

    @Override
    public List<FtReportMealVo> listAll() {
        return ftReportMealVoMapper.listAll();
    }

    @Override
    public List<FtReportMealVo> listNoPay() {
        return ftReportMealVoMapper.listNoPay();
    }

    @Override
    public List<FtReportMealVo> listPayoff() {
        return ftReportMealVoMapper.listPayoff();
    }

    @Override
    public Long countBillingBetween(ReportMealsDayEntity dao) {
        this.baseMapper.countBillingBetween(dao.getPatientId(), dao.getCreateAt(), dao.getSelectBillingDate());
//        FtReportMealsDao ftReportMealsDao = new FtReportMealsDao();
//        QueryWrapper<FtReportMealsDao> wrapper = new QueryWrapper<>();
//        wrapper
//                .eq("patient_id", dao.getPatientId())
//                .eq("settlement_flag", 0)
//                .between("create_at", dao.getCreateAt(), dao.getSelectBillingDate());
//        List<FtReportMealsDao> mealsDaoList = this.baseMapper.selectList(wrapper);
        return null;

    }

    @Override
    public List<FtReportMealsDao> listMealsWithInSettle(FtReportMealsDao ftReportMealsDao) {
        return this.baseMapper.listMealsWithInSettle(ftReportMealsDao);
    }

    @Override
    public Integer settleMeals(Long settlementId, Long patientId, String lastBillingDate, String selectBillingDate) {
        return this.baseMapper.settleMeals(settlementId, patientId, lastBillingDate, selectBillingDate);
    }

    @Override
    public ReportMealsPriceEntity sumTotalPrice(Long patientId, Date lastBillingDate, Date selectBillingDate) {
        return this.baseMapper.sumTotalPrice(patientId, lastBillingDate, selectBillingDate);
    }
}
