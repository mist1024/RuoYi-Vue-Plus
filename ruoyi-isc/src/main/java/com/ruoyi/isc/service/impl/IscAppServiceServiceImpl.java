package com.ruoyi.isc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ruoyi.common.constant.IscConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.isc.domain.IscAppService;
import com.ruoyi.isc.domain.IscAppServiceApply;
import com.ruoyi.isc.domain.IscApplication;
import com.ruoyi.isc.domain.IscService;
import com.ruoyi.isc.domain.bo.IscAppServiceApplyBo;
import com.ruoyi.isc.domain.bo.IscAppServiceBo;
import com.ruoyi.isc.domain.vo.IscAppServiceVo;
import com.ruoyi.isc.mapper.IscAppServiceMapper;
import com.ruoyi.isc.service.IIscAppServiceApplyService;
import com.ruoyi.isc.service.IIscAppServiceService;
import com.ruoyi.isc.service.IIscApplicationService;
import com.ruoyi.isc.service.IIscServiceService;
import com.ruoyi.isc.utils.RouteUtils;
import com.ruoyi.isc.utils.beans.IscRouteDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 应用服务Service业务层处理
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */
@Slf4j
@Service
public class IscAppServiceServiceImpl extends ServicePlusImpl<IscAppServiceMapper, IscAppService, IscAppServiceVo> implements IIscAppServiceService
{

    @Resource
    private IIscServiceService serviceService;
    @Resource
    private IIscAppServiceApplyService applyService;
    @Resource
    private IIscApplicationService applicationService;

    @PostConstruct
    public void init() {
        refreshRoutes();
    }

    @Override
    public IscAppServiceVo queryById(Long appServiceId)
    {
        IscAppServiceVo vo = getVoById(appServiceId);
        genServiceName(Arrays.asList(vo));
        return vo;
    }

    @Override
    public TableDataInfo<IscAppServiceVo> queryPageList(IscAppServiceBo bo)
    {
        PagePlus<IscAppService, IscAppServiceVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        genServiceName(result.getRecordsVo());
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<IscAppServiceVo> queryList(IscAppServiceBo bo)
    {
        final List<IscAppServiceVo> results = listVo(buildQueryWrapper(bo));
        genServiceName(results);
        return results;
    }

    /**
     * 组装服务名称
     *
     * @param results 应用服务列表
     */
    private void genServiceName(List<IscAppServiceVo> results)
    {
        if (CollectionUtil.isEmpty(results)) {
            return;
        }
        Set<Long> serviceIds = results.stream().map(IscAppServiceVo::getServiceId).collect(Collectors.toSet());
        Map<Long, String> nameMap = serviceService.getNameMap(serviceIds);
        for (IscAppServiceVo result : results) {
            result.setServiceName(nameMap.get(result.getServiceId()));
        }
    }

    private LambdaQueryWrapper<IscAppService> buildQueryWrapper(IscAppServiceBo bo)
    {
        Map<String, Object> params = bo.getParams();
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<IscAppService> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getServiceId() != null, IscAppService::getServiceId, bo.getServiceId());
        lqw.eq(bo.getApplicationId() != null, IscAppService::getApplicationId, bo.getApplicationId());
        lqw.eq(!SecurityUtils.isAdmin(userId), IscAppService::getUserId, userId);
        lqw.eq(StringUtils.isNotBlank(bo.getEnabled()), IscAppService::getEnabled, bo.getEnabled());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), IscAppService::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(IscAppServiceBo bo)
    {
        IscAppService add = BeanUtil.toBean(bo, IscAppService.class);
        validEntityBeforeSave(add);
        add.setStatus(IscConstants.AUDIT_WAIT);
        add.setUserId(SecurityUtils.getUserId());
        boolean result = save(add);
        addApplyRecord(bo, IscConstants.APPLY_TYPE_APPLY, add.getAppServiceId());
        return result;
    }

    @Override
    public Boolean updateByBo(IscAppServiceBo bo)
    {
        IscAppService appService = getById(bo.getAppServiceId());
        Assert.notNull(appService, () -> new ServiceException("申请信息不存在"));
        IscAppService update = new IscAppService().setAppServiceId(bo.getAppServiceId());
        validEntityBeforeSave(update);
        boolean result = true;
        boolean changeStatus = !IscConstants.AUDIT_PASS.equals(appService.getStatus());
        boolean changeRemark = !IscConstants.APPLY_TYPE_RENEWAL.equals(bo.getApplyType());
        if (changeStatus || changeRemark)
        {
            if (changeStatus)
            {
                update.setStatus(IscConstants.AUDIT_WAIT);
            }
            if (changeRemark)
            {
                update.setRemark(bo.getRemark());
            }
            result = updateById(update);
        }
        String applyType = IscConstants.APPLY_TYPE_RENEWAL.equals(bo.getApplyType())
                ? IscConstants.APPLY_TYPE_RENEWAL : IscConstants.APPLY_TYPE_MODIFY;
        addApplyRecord(bo, applyType, bo.getAppServiceId());
        return result;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(IscAppService entity)
    {
        //判断是否有申请未审核
        if (Objects.nonNull(entity.getAppServiceId())) {
            long count = applyService.count(Wrappers.<IscAppServiceApply>lambdaQuery()
                .eq(IscAppServiceApply::getAppServiceId, entity.getAppServiceId())
                .eq(IscAppServiceApply::getStatus, IscConstants.AUDIT_WAIT));
            Assert.isFalse(SqlHelper.retBool(count), () -> new ServiceException("有申请未审核, 不能再申请"));
        }
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid)
    {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        applyService.remove(Wrappers.<IscAppServiceApply>lambdaQuery().in(IscAppServiceApply::getAppServiceId, ids));
        return removeByIds(ids);
    }

    @Override
    public List<Tree<Long>> genAppServiceTree(Long applicationId)
    {
        List<Long> serviceIds = listObjs(Wrappers.<IscAppService>lambdaQuery()
            .select(IscAppService::getServiceId)
            .eq(IscAppService::getApplicationId, applicationId), o -> (Long) o);
        return serviceService.genServiceTree(CollectionUtil.newHashSet(serviceIds));
    }

    @Override
    public List<IscAppService> getAppServiceListByIds(Collection<Long> ids)
    {
        return list(Wrappers.<IscAppService>lambdaQuery()
            .select(IscAppService::getAppServiceId, IscAppService::getApplicationId, IscAppService::getServiceId)
            .in(IscAppService::getAppServiceId, ids));
    }

    @Override
    public void refreshRoutes()
    {
        List<IscAppService> list = list(Wrappers.<IscAppService>lambdaQuery()
                .eq(IscAppService::getEnabled, UserConstants.NORMAL)
                .eq(IscAppService::getStatus, IscConstants.AUDIT_PASS)
                .gt(IscAppService::getEndTime, DateUtils.getNowDate()));
        if(CollectionUtil.isEmpty(list)) {
            return;
        }
        Set<Long> serviceIds = list.stream().map(IscAppService::getServiceId).collect(Collectors.toSet());
        Map<Long, IscService> serviceMap = serviceService.list(Wrappers.<IscService>lambdaQuery()
                .select(IscService::getServiceId, IscService::getServiceAddr, IscService::getHiddenParams, IscService::getRequestMethod)
                .in(IscService::getServiceId, serviceIds)).stream()
                .collect(Collectors.toMap(IscService::getServiceId, Function.identity()));
        Set<Long> applicationIds = list.stream().map(IscAppService::getApplicationId).collect(Collectors.toSet());
        Map<Long, String> accessKeyMap = applicationService.list(Wrappers.<IscApplication>lambdaQuery()
                .select(IscApplication::getApplicationId, IscApplication::getAccessKey)
                .in(IscApplication::getApplicationId, applicationIds)).stream()
                .collect(Collectors.toMap(IscApplication::getApplicationId, IscApplication::getAccessKey));
        List<IscRouteDefinition> routes = new ArrayList<>();

        for (IscAppService appService : list)
        {
            IscService service = serviceMap.get(appService.getServiceId());
            if(Objects.isNull(service)) {
                log.error("路由初始化失败: id:[{}],服务[{}]信息不存在！", appService.getAppServiceId(), appService.getServiceId());
                continue;
            }
            String ak = accessKeyMap.get(appService.getApplicationId());
            if(StringUtils.isBlank(ak)) {
                log.error("路由初始化失败: id:[{}],ak[{}]信息不存在！", appService.getAppServiceId(), appService.getApplicationId());
                continue;
            }
            routes.add(RouteUtils.generateRoute(appService, service, ak));
        }
        RouteUtils.refreshRoute(routes);
    }

    /**
     * 添加 服务申请记录
     *
     * @param appService   应用服务信息
     * @param applyType    申请类型
     * @param appServiceId 应用服务ID
     * @return 是否添加成功
     */
    private Boolean addApplyRecord(IscAppServiceBo appService, String applyType, Long appServiceId)
    {
        IscAppServiceApplyBo entity = new IscAppServiceApplyBo();
        entity.setAppServiceId(appServiceId);
        entity.setApplyType(applyType);
        entity.setStatus(IscConstants.AUDIT_WAIT);
        entity.setRemark(appService.getRemark());
        switch (applyType) {
            case IscConstants.APPLY_TYPE_APPLY:
            case IscConstants.APPLY_TYPE_MODIFY:
                entity.setRenewalDuration(appService.getRenewalDuration());
                entity.setQuotaDays(appService.getQuotaDays());
                entity.setQuotaHours(appService.getQuotaHours());
                entity.setQuotaMinutes(appService.getQuotaMinutes());
                entity.setQuotaSeconds(appService.getQuotaSeconds());
                break;
            case IscConstants.APPLY_TYPE_RENEWAL:
                entity.setRenewalDuration(appService.getRenewalDuration());
                break;
            default:
        }
        return applyService.insertByBo(entity);
    }
}
