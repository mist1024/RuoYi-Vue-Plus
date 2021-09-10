package com.ruoyi.isc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.constant.IscConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.FullPathUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.isc.domain.IscService;
import com.ruoyi.isc.domain.bo.IscAuditBo;
import com.ruoyi.isc.domain.bo.IscServiceBo;
import com.ruoyi.isc.domain.vo.IscServiceVo;
import com.ruoyi.isc.mapper.IscServiceMapper;
import com.ruoyi.isc.service.IIscServiceCateService;
import com.ruoyi.isc.service.IIscServiceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务信息Service业务层处理
 *
 * @author Wenchao Gong
 * @date 2021-08-22
 */
@Service
public class IscServiceServiceImpl extends ServicePlusImpl<IscServiceMapper, IscService, IscServiceVo> implements IIscServiceService {

    @Resource
    private IIscServiceCateService cateService;

    @Override
    public IscServiceVo queryById(Long serviceId)
    {
        final IscServiceVo vo = getVoById(serviceId);
        genVoInfo(Arrays.asList(vo));
        return vo;
    }

    @Override
    public TableDataInfo<IscServiceVo> queryPageList(IscServiceBo bo)
    {
        PagePlus<IscService, IscServiceVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        genVoInfo(result.getRecordsVo());
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<IscServiceVo> queryList(IscServiceBo bo)
    {
        final List<IscServiceVo> results = listVo(buildQueryWrapper(bo));
        genVoInfo(results);
        return results;
    }

    private void genVoInfo(List<IscServiceVo> results)
    {
        if (CollectionUtils.isNotEmpty(results))
        {
            Map<String, String> cateNameMap = cateService.batchCateName(results.stream()
                    .map(IscServiceVo::getCateFullPath).collect(Collectors.toSet()));
            for (IscServiceVo result : results)
            {
                result.setCateName(cateNameMap.get(result.getCateFullPath()));
            }
        }
    }

    private LambdaQueryWrapper<IscService> buildQueryWrapper(IscServiceBo bo)
    {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IscService> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getServiceName()), IscService::getServiceName, bo.getServiceName());
        lqw.eq(StringUtils.isNotBlank(bo.getOnlineStatus()), IscService::getOnlineStatus, bo.getOnlineStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), IscService::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getEnabled()), IscService::getEnabled, bo.getEnabled());
        lqw.ge(StringUtils.isNotBlank(bo.getCateFullPath()), IscService::getCateFullPath, bo.getCateFullPath());
        lqw.le(StringUtils.isNotBlank(bo.getCateFullPath()), IscService::getCateFullPath, FullPathUtils.genMaxFullPath(bo.getCateFullPath()));
        Long userId = SecurityUtils.getUserId();
        lqw.eq(!SecurityUtils.isAdmin(userId), IscService::getUserId, userId);
        return lqw;
    }

    @Override
    public Boolean insertByBo(IscServiceBo bo)
    {
        IscService add = BeanUtil.toBean(bo, IscService.class);
        validEntityBeforeSave(add);
        add.setUserId(SecurityUtils.getUserId());
        add.setOnlineStatus(IscConstants.ONLINE_STATUS_ON);
        add.setStatus(IscConstants.AUDIT_WAIT);
        return save(add);
    }

    @Override
    public Boolean updateByBo(IscServiceBo bo)
    {
        IscService update = BeanUtil.toBean(bo, IscService.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(IscService entity)
    {
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid)
    {
        if (isValid)
        {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }

    @Override
    public Map<Long, String> getNameMap(Collection<Long> serviceIds)
    {
        if(CollectionUtil.isEmpty(serviceIds)) {
            return MapUtil.empty();
        }
        return list(Wrappers.<IscService>lambdaQuery()
                .select(IscService::getServiceId, IscService::getServiceName)
                .in(IscService::getServiceId, serviceIds)).stream()
                .collect(Collectors.toMap(IscService::getServiceId, IscService::getServiceName));
    }

    @Override
    public List<Tree<Long>> genServiceTree()
    {
        return genServiceTree(CollectionUtil.newHashSet());
    }

    @Override
    public List<Tree<Long>> genServiceTree(Set<Long> exitsIds)
    {
        List<IscService> serviceList = list(Wrappers.<IscService>lambdaQuery()
                .select(IscService::getServiceId, IscService::getServiceName, IscService::getCateFullPath)
                .eq(IscService::getStatus, IscConstants.AUDIT_PASS)
                .eq(IscService::getEnabled, UserConstants.DICT_NORMAL)
                .orderByDesc(IscService::getUpdateTime));
        return cateService.genCateTree(cateService.selectCateList(), serviceList, exitsIds);
    }

    @Override
    public Boolean audit(IscAuditBo bo)
    {
        checkAuditBO(bo);
        boolean result = true;
        for (Long id : bo.getIds())
        {
            IscService service = getOne(Wrappers.<IscService>lambdaQuery()
                .eq(IscService::getServiceId, id)
                .eq(IscService::getStatus, IscConstants.AUDIT_WAIT), false);
            if(Objects.isNull(service)) {
                continue;
            }
            result = updateById(new IscService().setServiceId(id).setStatus(bo.getStatus())
                .setAuditMind(bo.getRemark()));
        }
        return result;
    }

    @Override
    public void checkAuditBO(IscAuditBo bo)
    {
        switch (bo.getStatus()) {
            case IscConstants.AUDIT_PASS:
                break;
            case IscConstants.AUDIT_REJECT:
                Assert.notBlank(bo.getRemark(), () -> new ServiceException("审核意见不能为空"));
                break;
            default:
                throw new ServiceException("审核状态异常");
        }
    }
}
