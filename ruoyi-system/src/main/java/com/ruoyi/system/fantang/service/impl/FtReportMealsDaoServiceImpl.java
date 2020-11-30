package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import com.ruoyi.system.fantang.mapper.FtReportMealVoMapper;
import com.ruoyi.system.fantang.mapper.FtReportMealsDaoMapper;
import com.ruoyi.system.fantang.service.IFtReportMealsDaoService;
import com.ruoyi.system.fantang.vo.FtReportMealVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
