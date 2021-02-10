package com.ruoyi.system.huiyuan.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.system.huiyuan.mapper.HyProjTalentDaoMapper;
import com.ruoyi.system.huiyuan.domain.HyProjTalentDao;
import com.ruoyi.system.huiyuan.service.IHyProjTalentDaoService;

import java.util.List;
import java.util.Map;

/**
 * 优才项目Service业务层处理
 *
 * @author ryo
 * @date 2021-01-09
 */
@Service
public class HyProjTalentDaoServiceImpl extends ServiceImpl<HyProjTalentDaoMapper, HyProjTalentDao> implements IHyProjTalentDaoService {

    @Override
    public List<HyProjTalentDao> queryList(HyProjTalentDao hyProjTalentDao) {
        LambdaQueryWrapper<HyProjTalentDao> lqw = Wrappers.lambdaQuery();
        if (hyProjTalentDao.getPostType() != null){
            lqw.eq(HyProjTalentDao::getPostType ,hyProjTalentDao.getPostType());
        }
        if (StringUtils.isNotBlank(hyProjTalentDao.getShopList())){
            lqw.eq(HyProjTalentDao::getShopList ,hyProjTalentDao.getShopList());
        }
        if (StringUtils.isNotBlank(hyProjTalentDao.getHomeUrl())){
            lqw.eq(HyProjTalentDao::getHomeUrl ,hyProjTalentDao.getHomeUrl());
        }
        if (hyProjTalentDao.getCreateAt() != null){
            lqw.eq(HyProjTalentDao::getCreateAt ,hyProjTalentDao.getCreateAt());
        }
        if (hyProjTalentDao.getUpdateAt() != null){
            lqw.eq(HyProjTalentDao::getUpdateAt ,hyProjTalentDao.getUpdateAt());
        }
        return this.list(lqw);
    }
}
