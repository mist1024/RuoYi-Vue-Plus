package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.domain.FtPrepaymentDao;
import com.ruoyi.system.fantang.domain.FtPrepaymentVo;
import com.ruoyi.system.fantang.mapper.FtPrepaymentDaoMapper;
import com.ruoyi.system.fantang.service.IFtPrepaymentDaoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 收费管理Service业务层处理
 *
 * @author ft
 * @date 2020-11-19
 */
@Service
public class FtPrepaymentDaoServiceImpl extends ServiceImpl<FtPrepaymentDaoMapper, FtPrepaymentDao> implements IFtPrepaymentDaoService {

    @Override
    public List<FtPrepaymentVo> listNoPrepay(FtPrepaymentVo params) {
        return this.baseMapper.listNoPrepay(params);
    }

    @Override
    public List<FtPrepaymentVo> listPrepay(FtPrepaymentVo params) {
        return this.baseMapper.listPrepay(params);
    }

    @Override
    public List<FtPrepaymentVo> listAllPrepay(FtPrepaymentVo params) {
        return this.baseMapper.listAllPrepay(params);
    }

    @Override
    public FtPrepaymentVo getCountById(Long patiendId) {
        QueryWrapper<FtPrepaymentDao> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_Id", patiendId);
        FtPrepaymentDao prepaymentDao = this.baseMapper.selectOne(queryWrapper);
        return (FtPrepaymentVo) prepaymentDao;

    }

    @Override
    public FtPrepaymentDao getByPatientId(Long patientId) {
        QueryWrapper<FtPrepaymentDao> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_Id", patientId);
        return this.baseMapper.selectOne(queryWrapper);
    }
}
