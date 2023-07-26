package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.BuyHousesReviewMember;
import com.ruoyi.system.domain.bo.BuyHousesReviewMemberBo;
import com.ruoyi.system.domain.vo.BuyHousesReviewMemberVo;
import com.ruoyi.system.mapper.BuyHousesReviewMemberMapper;
import com.ruoyi.system.service.IBuyHousesReviewMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 购房复审家属关系Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-15
 */
@RequiredArgsConstructor
@Service
public class BuyHousesReviewMemberServiceImpl implements IBuyHousesReviewMemberService {

    private final BuyHousesReviewMemberMapper baseMapper;

    /**
     * 查询购房复审家属关系
     */
    @Override
    public BuyHousesReviewMemberVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询购房复审家属关系列表
     */
    @Override
    public TableDataInfo<BuyHousesReviewMemberVo> queryPageList(BuyHousesReviewMemberBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BuyHousesReviewMember> lqw = buildQueryWrapper(bo);
        Page<BuyHousesReviewMemberVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询购房复审家属关系列表
     */
    @Override
    public List<BuyHousesReviewMemberVo> queryList(BuyHousesReviewMemberBo bo) {
        LambdaQueryWrapper<BuyHousesReviewMember> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BuyHousesReviewMember> buildQueryWrapper(BuyHousesReviewMemberBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BuyHousesReviewMember> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getInsidepageUrl()), BuyHousesReviewMember::getInsidepageUrl, bo.getInsidepageUrl());
        lqw.eq(bo.getBuyHousesId() != null, BuyHousesReviewMember::getBuyHousesId, bo.getBuyHousesId());
        lqw.eq(StringUtils.isNotBlank(bo.getFrontUrl()), BuyHousesReviewMember::getFrontUrl, bo.getFrontUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getRelation()), BuyHousesReviewMember::getRelation, bo.getRelation());
        lqw.eq(StringUtils.isNotBlank(bo.getReverseUrl()), BuyHousesReviewMember::getReverseUrl, bo.getReverseUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getHomeRecordUrl()), BuyHousesReviewMember::getHomeRecordUrl, bo.getHomeRecordUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getCardId()), BuyHousesReviewMember::getCardId, bo.getCardId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), BuyHousesReviewMember::getName, bo.getName());
        return lqw;
    }

    /**
     * 新增购房复审家属关系
     */
    @Override
    public Boolean insertByBo(BuyHousesReviewMemberBo bo) {
        BuyHousesReviewMember add = BeanUtil.toBean(bo, BuyHousesReviewMember.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改购房复审家属关系
     */
    @Override
    public Boolean updateByBo(BuyHousesReviewMemberBo bo) {
        BuyHousesReviewMember update = BeanUtil.toBean(bo, BuyHousesReviewMember.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BuyHousesReviewMember entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除购房复审家属关系
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
