package com.ruoyi.system.fantang.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.system.fantang.mapper.FtReturnDaoMapper;
import com.ruoyi.system.fantang.domain.FtReturnDao;
import com.ruoyi.system.fantang.service.IFtReturnDaoService;

import java.util.List;
import java.util.Map;

/**
 * 回款登记Service业务层处理
 *
 * @author ft
 * @date 2021-01-04
 */
@Service
public class FtReturnDaoServiceImpl extends ServiceImpl<FtReturnDaoMapper, FtReturnDao> implements IFtReturnDaoService {

    @Override
    public List<FtReturnDao> queryList(FtReturnDao ftReturnDao) {
        LambdaQueryWrapper<FtReturnDao> lqw = Wrappers.lambdaQuery();
        if (ftReturnDao.getInvoiceId() != null){
            lqw.eq(FtReturnDao::getInvoiceId ,ftReturnDao.getInvoiceId());
        }
        if (ftReturnDao.getReturnAt() != null){
            lqw.eq(FtReturnDao::getReturnAt ,ftReturnDao.getReturnAt());
        }
        if (ftReturnDao.getReturnPrice() != null){
            lqw.eq(FtReturnDao::getReturnPrice ,ftReturnDao.getReturnPrice());
        }
        if (ftReturnDao.getBalancePrice() != null){
            lqw.eq(FtReturnDao::getBalancePrice ,ftReturnDao.getBalancePrice());
        }
        if (StringUtils.isNotBlank(ftReturnDao.getOpera())){
            lqw.eq(FtReturnDao::getOpera ,ftReturnDao.getOpera());
        }
        if (StringUtils.isNotBlank(ftReturnDao.getVoucherUrl())){
            lqw.eq(FtReturnDao::getVoucherUrl ,ftReturnDao.getVoucherUrl());
        }
        if (ftReturnDao.getReturnFlag() != null){
            lqw.eq(FtReturnDao::getReturnFlag ,ftReturnDao.getReturnFlag());
        }
        return this.list(lqw);
    }
}
