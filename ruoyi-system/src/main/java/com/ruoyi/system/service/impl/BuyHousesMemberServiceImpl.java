package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.BuyHousesMember;
import com.ruoyi.system.domain.bo.BuyHousesMemberBo;
import com.ruoyi.system.domain.vo.BuyHousesMemberVo;
import com.ruoyi.system.mapper.BuyHousesMemberMapper;
import com.ruoyi.system.service.IBuyHousesMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 购房家属关系Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-15
 */
@RequiredArgsConstructor
@Service
public class BuyHousesMemberServiceImpl implements IBuyHousesMemberService {

    private final BuyHousesMemberMapper baseMapper;

    /**
     * 查询购房家属关系
     */
    @Override
    public BuyHousesMemberVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询购房家属关系列表
     */
    @Override
    public TableDataInfo<BuyHousesMemberVo> queryPageList(BuyHousesMemberBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BuyHousesMember> lqw = buildQueryWrapper(bo);
        Page<BuyHousesMemberVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询购房家属关系列表
     */
    @Override
    public List<BuyHousesMemberVo> queryList(BuyHousesMemberBo bo) {
        LambdaQueryWrapper<BuyHousesMember> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BuyHousesMember> buildQueryWrapper(BuyHousesMemberBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BuyHousesMember> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getInsidepageUrl()), BuyHousesMember::getInsidepageUrl, bo.getInsidepageUrl());
        lqw.eq(bo.getBuyHousesId() != null, BuyHousesMember::getBuyHousesId, bo.getBuyHousesId());
        lqw.eq(StringUtils.isNotBlank(bo.getFrontUrl()), BuyHousesMember::getFrontUrl, bo.getFrontUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getRelation()), BuyHousesMember::getRelation, bo.getRelation());
        lqw.eq(StringUtils.isNotBlank(bo.getReverseUrl()), BuyHousesMember::getReverseUrl, bo.getReverseUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getHomeRecordUrl()), BuyHousesMember::getHomeRecordUrl, bo.getHomeRecordUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getCardId()), BuyHousesMember::getCardId, bo.getCardId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), BuyHousesMember::getName, bo.getName());
        return lqw;
    }

    /**
     * 新增购房家属关系
     */
    @Override
    public Boolean insertByBo(BuyHousesMemberBo bo) {
        BuyHousesMember add = BeanUtil.toBean(bo, BuyHousesMember.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改购房家属关系
     */
    @Override
    public Boolean updateByBo(BuyHousesMemberBo bo) {
        BuyHousesMember update = BeanUtil.toBean(bo, BuyHousesMember.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BuyHousesMember entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除购房家属关系
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
