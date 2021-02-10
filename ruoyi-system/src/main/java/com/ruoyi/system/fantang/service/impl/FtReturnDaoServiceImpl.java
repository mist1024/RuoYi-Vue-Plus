package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtReturnDao;
import com.ruoyi.system.fantang.mapper.FtReturnDaoMapper;
import com.ruoyi.system.fantang.service.IFtReturnDaoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 回款登记Service业务层处理
 *
 * @author ft
 * @date 2021-01-25
 */
@Service
public class FtReturnDaoServiceImpl extends ServiceImpl<FtReturnDaoMapper, FtReturnDao> implements IFtReturnDaoService {

    @Override
    public List<FtReturnDao> queryList(FtReturnDao ftReturnDao) {
        LambdaQueryWrapper<FtReturnDao> lqw = Wrappers.lambdaQuery();
        if (ftReturnDao.getInvoiceId() != null) {
            lqw.eq(FtReturnDao::getInvoiceId, ftReturnDao.getInvoiceId());
        }
        if (ftReturnDao.getReturnAt() != null) {
            lqw.eq(FtReturnDao::getReturnAt, ftReturnDao.getReturnAt());
        }
        if (ftReturnDao.getReturnPrice() != null) {
            lqw.eq(FtReturnDao::getReturnPrice, ftReturnDao.getReturnPrice());
        }
        if (ftReturnDao.getBalancePrice() != null) {
            lqw.eq(FtReturnDao::getBalancePrice, ftReturnDao.getBalancePrice());
        }
        if (StringUtils.isNotBlank(ftReturnDao.getOpera())) {
            lqw.eq(FtReturnDao::getOpera, ftReturnDao.getOpera());
        }
        if (StringUtils.isNotBlank(ftReturnDao.getVoucherUrl())) {
            lqw.eq(FtReturnDao::getVoucherUrl, ftReturnDao.getVoucherUrl());
        }
        if (ftReturnDao.getReturnFlag() != null) {
            lqw.eq(FtReturnDao::getReturnFlag, ftReturnDao.getReturnFlag());
        }
        return this.list(lqw);
    }

    @Override
    public FtReturnDao selectLastReturn(Long id) {
        QueryWrapper<FtReturnDao> wrapper = new QueryWrapper<>();
        wrapper.eq("invoice_id", id);
        wrapper.orderByDesc("return_at");
        wrapper.last("limit 1");

        return this.baseMapper.selectOne(wrapper);
    }
}
