package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.BuyHouses;
import com.ruoyi.system.domain.BuyHousesMember;
import com.ruoyi.system.domain.bo.BuyHousesBo;
import com.ruoyi.system.domain.vo.BuyHousesVo;
import com.ruoyi.system.mapper.BuyHousesMapper;
import com.ruoyi.system.mapper.BuyHousesMemberMapper;
import com.ruoyi.system.service.IBuyHousesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-24
 */
@RequiredArgsConstructor
@Service
public class BuyHousesServiceImpl implements IBuyHousesService {

    private final BuyHousesMapper baseMapper;

    private final BuyHousesMemberMapper buyHousesMemberMapper;

    /**
     * 查询【请填写功能名称】
     */
    @Override
    public BuyHousesVo queryById(Long id){
        BuyHousesVo buyHousesVo = baseMapper.selectVoById(id);
        LambdaQueryWrapper<BuyHousesMember> queryWrapper = new LambdaQueryWrapper<BuyHousesMember>()
            .eq(BuyHousesMember::getBuyHousesId, id);
        List<BuyHousesMember> buyHousesMembers = buyHousesMemberMapper.selectList(queryWrapper);
        buyHousesVo.setBuyHousesMemberList(buyHousesMembers);
        return buyHousesVo;
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<BuyHousesVo> queryPageList(BuyHousesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BuyHouses> lqw = buildQueryWrapper(bo);
        Page<BuyHousesVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<BuyHousesVo> queryList(BuyHousesBo bo) {
        LambdaQueryWrapper<BuyHouses> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BuyHouses> buildQueryWrapper(BuyHousesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BuyHouses> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getInsidepageUrl()), BuyHouses::getInsidepageUrl, bo.getInsidepageUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getCardId()), BuyHouses::getCardId, bo.getCardId());
        lqw.eq(StringUtils.isNotBlank(bo.getCommitmentUrl()), BuyHouses::getCommitmentUrl, bo.getCommitmentUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getCompanyAddress()), BuyHouses::getCompanyAddress, bo.getCompanyAddress());
        lqw.like(StringUtils.isNotBlank(bo.getCompanyName()), BuyHouses::getCompanyName, bo.getCompanyName());
        lqw.eq(bo.getCreateTime() != null, BuyHouses::getCreateTime, bo.getCreateTime());
        lqw.eq(StringUtils.isNotBlank(bo.getDeclarationUrl()), BuyHouses::getDeclarationUrl, bo.getDeclarationUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getDistrict()), BuyHouses::getDistrict, bo.getDistrict());
        lqw.eq(StringUtils.isNotBlank(bo.getEducation()), BuyHouses::getEducation, bo.getEducation());
        lqw.eq(StringUtils.isNotBlank(bo.getFrontUrl()), BuyHouses::getFrontUrl, bo.getFrontUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getGyStatus()), BuyHouses::getGyStatus, bo.getGyStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getHomeRecordUrl()), BuyHouses::getHomeRecordUrl, bo.getHomeRecordUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getHomepageUrl()), BuyHouses::getHomepageUrl, bo.getHomepageUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getLaborContractUrl()), BuyHouses::getLaborContractUrl, bo.getLaborContractUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getLicenseUrl()), BuyHouses::getLicenseUrl, bo.getLicenseUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getMaritalStatus()), BuyHouses::getMaritalStatus, bo.getMaritalStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getMaritalUrl()), BuyHouses::getMaritalUrl, bo.getMaritalUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getNationality()), BuyHouses::getNationality, bo.getNationality());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), BuyHouses::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getQyStatus()), BuyHouses::getQyStatus, bo.getQyStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getReverseUrl()), BuyHouses::getReverseUrl, bo.getReverseUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getSex()), BuyHouses::getSex, bo.getSex());
        lqw.eq(StringUtils.isNotBlank(bo.getShStatus()), BuyHouses::getShStatus, bo.getShStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getSocialCode()), BuyHouses::getSocialCode, bo.getSocialCode());
        lqw.eq(StringUtils.isNotBlank(bo.getSocialSecurityUrl()), BuyHouses::getSocialSecurityUrl, bo.getSocialSecurityUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), BuyHouses::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), BuyHouses::getType, bo.getType());
        lqw.eq(bo.getUserId() != null, BuyHouses::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), BuyHouses::getUserName, bo.getUserName());
        lqw.eq(bo.getAffTime() != null, BuyHouses::getAffTime, bo.getAffTime());
        lqw.eq(StringUtils.isNotBlank(bo.getPictureInformationUrl()), BuyHouses::getPictureInformationUrl, bo.getPictureInformationUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getWorkAddress()), BuyHouses::getWorkAddress, bo.getWorkAddress());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    public Boolean insertByBo(BuyHousesBo bo) {
        BuyHouses add = BeanUtil.toBean(bo, BuyHouses.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改【请填写功能名称】
     */
    @Override
    public Boolean updateByBo(BuyHousesBo bo) {
        BuyHouses update = BeanUtil.toBean(bo, BuyHouses.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BuyHouses entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除【请填写功能名称】
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
