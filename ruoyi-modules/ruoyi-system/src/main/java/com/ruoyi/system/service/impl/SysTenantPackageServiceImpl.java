package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.SysTenantPackageBo;
import com.ruoyi.system.domain.vo.SysTenantPackageVo;
import com.ruoyi.system.domain.SysTenantPackage;
import com.ruoyi.system.mapper.SysTenantPackageMapper;
import com.ruoyi.system.service.ISysTenantPackageService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 租户套餐Service业务层处理
 *
 * @author Michelle.Chung
 */
@RequiredArgsConstructor
@Service
public class SysTenantPackageServiceImpl implements ISysTenantPackageService {

    private final SysTenantPackageMapper baseMapper;

    /**
     * 查询租户套餐
     */
    @Override
    public SysTenantPackageVo queryById(Long packageId){
        return baseMapper.selectVoById(packageId);
    }

    /**
     * 查询租户套餐列表
     */
    @Override
    public TableDataInfo<SysTenantPackageVo> queryPageList(SysTenantPackageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysTenantPackage> lqw = buildQueryWrapper(bo);
        Page<SysTenantPackageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询租户套餐列表
     */
    @Override
    public List<SysTenantPackageVo> queryList(SysTenantPackageBo bo) {
        LambdaQueryWrapper<SysTenantPackage> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysTenantPackage> buildQueryWrapper(SysTenantPackageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysTenantPackage> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getPackageName()), SysTenantPackage::getPackageName, bo.getPackageName());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysTenantPackage::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增租户套餐
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(SysTenantPackageBo bo) {
        SysTenantPackage add = BeanUtil.toBean(bo, SysTenantPackage.class);
        validEntityBeforeSave(add);
        // 保存菜单id
        List<Long> menuIds = Arrays.asList(bo.getMenuIds());
        if (CollUtil.isNotEmpty(menuIds)) {
            add.setMenuIds(StringUtils.join(menuIds, ", "));
        } else {
            add.setMenuIds("");
        }
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPackageId(add.getPackageId());
        }
        return flag;
    }

    /**
     * 修改租户套餐
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(SysTenantPackageBo bo) {
        SysTenantPackage update = BeanUtil.toBean(bo, SysTenantPackage.class);
        validEntityBeforeSave(update);
        // 保存菜单id
        List<Long> menuIds = Arrays.asList(bo.getMenuIds());
        if (CollUtil.isNotEmpty(menuIds)) {
            update.setMenuIds(StringUtils.join(menuIds, ", "));
        } else {
            update.setMenuIds("");
        }
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 修改套餐状态
     *
     * @param bo 套餐信息
     * @return 结果
     */
    @Override
    public int updatePackageStatus(SysTenantPackageBo bo) {
        SysTenantPackage tenantPackage = BeanUtil.toBean(bo, SysTenantPackage.class);
        return baseMapper.updateById(tenantPackage);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysTenantPackage entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除租户套餐
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
