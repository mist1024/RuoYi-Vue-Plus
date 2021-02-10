package com.ruoyi.system.huiyuan.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.system.huiyuan.mapper.HyMemberDaoMapper;
import com.ruoyi.system.huiyuan.domain.HyMemberDao;
import com.ruoyi.system.huiyuan.service.IHyMemberDaoService;

import java.util.List;
import java.util.Map;

/**
 * 会员Service业务层处理
 *
 * @author ryo
 * @date 2021-01-09
 */
@Service
public class HyMemberDaoServiceImpl extends ServiceImpl<HyMemberDaoMapper, HyMemberDao> implements IHyMemberDaoService {

    @Override
    public List<HyMemberDao> queryList(HyMemberDao hyMemberDao) {
        LambdaQueryWrapper<HyMemberDao> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(hyMemberDao.getTel())){
            lqw.eq(HyMemberDao::getTel ,hyMemberDao.getTel());
        }
        if (StringUtils.isNotBlank(hyMemberDao.getPassword())){
            lqw.eq(HyMemberDao::getPassword ,hyMemberDao.getPassword());
        }
        if (hyMemberDao.getProjType() != null){
            lqw.eq(HyMemberDao::getProjType ,hyMemberDao.getProjType());
        }
        if (hyMemberDao.getPoints() != null){
            lqw.eq(HyMemberDao::getPoints ,hyMemberDao.getPoints());
        }
        if (hyMemberDao.getVoucher() != null){
            lqw.eq(HyMemberDao::getVoucher ,hyMemberDao.getVoucher());
        }
        if (hyMemberDao.getBalance() != null){
            lqw.eq(HyMemberDao::getBalance ,hyMemberDao.getBalance());
        }
        if (StringUtils.isNotBlank(hyMemberDao.getDevice())){
            lqw.eq(HyMemberDao::getDevice ,hyMemberDao.getDevice());
        }
        if (StringUtils.isNotBlank(hyMemberDao.getMemberUrl())){
            lqw.eq(HyMemberDao::getMemberUrl ,hyMemberDao.getMemberUrl());
        }
        if (hyMemberDao.getCreateAt() != null){
            lqw.eq(HyMemberDao::getCreateAt ,hyMemberDao.getCreateAt());
        }
        if (hyMemberDao.getUpdateAt() != null){
            lqw.eq(HyMemberDao::getUpdateAt ,hyMemberDao.getUpdateAt());
        }
        if (hyMemberDao.getProjId() != null){
            lqw.eq(HyMemberDao::getProjId ,hyMemberDao.getProjId());
        }
        return this.list(lqw);
    }
}
