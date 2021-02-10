package com.ruoyi.system.huiyuan.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.system.huiyuan.mapper.HyProjDaoMapper;
import com.ruoyi.system.huiyuan.domain.HyProjDao;
import com.ruoyi.system.huiyuan.service.IHyProjDaoService;

import java.util.List;
import java.util.Map;

/**
 * 项目信息Service业务层处理
 *
 * @author ryo
 * @date 2021-01-09
 */
@Service
public class HyProjDaoServiceImpl extends ServiceImpl<HyProjDaoMapper, HyProjDao> implements IHyProjDaoService {

    @Override
    public List<HyProjDao> queryList(HyProjDao hyProjDao) {
        LambdaQueryWrapper<HyProjDao> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(hyProjDao.getProjName())){
            lqw.like(HyProjDao::getProjName ,hyProjDao.getProjName());
        }
        if (StringUtils.isNotBlank(hyProjDao.getHomeUrl())){
            lqw.eq(HyProjDao::getHomeUrl ,hyProjDao.getHomeUrl());
        }
        if (hyProjDao.getCreateAt() != null){
            lqw.eq(HyProjDao::getCreateAt ,hyProjDao.getCreateAt());
        }
        if (hyProjDao.getUpdateAt() != null){
            lqw.eq(HyProjDao::getUpdateAt ,hyProjDao.getUpdateAt());
        }
        return this.list(lqw);
    }
}
