package com.ruoyi.isc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.constant.IscConstants;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.isc.domain.IscAppService;
import com.ruoyi.isc.domain.IscAppServiceApply;
import com.ruoyi.isc.domain.IscApplication;
import com.ruoyi.isc.domain.IscService;
import com.ruoyi.isc.domain.bo.IscAppServiceApplyBo;
import com.ruoyi.isc.domain.bo.IscAuditBo;
import com.ruoyi.isc.domain.vo.IscAppServiceApplyVo;
import com.ruoyi.isc.mapper.IscAppServiceApplyMapper;
import com.ruoyi.isc.service.IIscAppServiceApplyService;
import com.ruoyi.isc.service.IIscAppServiceService;
import com.ruoyi.isc.service.IIscApplicationService;
import com.ruoyi.isc.service.IIscServiceService;
import com.ruoyi.isc.utils.RouteUtils;
import com.ruoyi.isc.utils.beans.IscRouteDefinition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 应用服务申请信息Service业务层处理
 *
 * @author Wenchao Gong
 * @date 2021-09-09
 */
@Service
public class IscAppServiceApplyServiceImpl extends ServicePlusImpl<IscAppServiceApplyMapper, IscAppServiceApply, IscAppServiceApplyVo> implements IIscAppServiceApplyService {

    @Resource
    private IIscAppServiceService appServiceService;
    @Resource
    private IIscApplicationService applicationService;
    @Resource
    private IIscServiceService serviceService;

    @Override
    public IscAppServiceApplyVo queryById(Long applyId){
        IscAppServiceApplyVo vo = getVoById(applyId);
        genServiceNameAndApplicationName(Arrays.asList(vo));
        return vo;
    }

    @Override
    public TableDataInfo<IscAppServiceApplyVo> queryPageList(IscAppServiceApplyBo bo) {
        PagePlus<IscAppServiceApply, IscAppServiceApplyVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        genServiceNameAndApplicationName(result.getRecordsVo());
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<IscAppServiceApplyVo> queryList(IscAppServiceApplyBo bo) {
        List<IscAppServiceApplyVo> results = listVo(buildQueryWrapper(bo));
        genServiceNameAndApplicationName(results);
        return results;
    }

    /**
     * 组装服务、应用名称
     * @param list 申请记录
     */
    private void genServiceNameAndApplicationName(List<IscAppServiceApplyVo> list)
    {
        if(CollectionUtil.isEmpty(list)) {
            return;
        }
        Set<Long> appServiceIds = list.stream().map(IscAppServiceApplyVo::getAppServiceId).collect(Collectors.toSet());
        List<IscAppService> appServiceList = appServiceService.getAppServiceListByIds(appServiceIds);
        if(CollectionUtil.isEmpty(appServiceList)) {
            return;
        }
        Map<Long, IscAppService> appServiceMap = appServiceList.stream()
            .collect(Collectors.toMap(IscAppService::getAppServiceId, Function.identity()));
        Map<Long, String> serviceNameMap = serviceService.getNameMap(appServiceList.stream()
            .map(IscAppService::getServiceId).collect(Collectors.toSet()));
        Map<Long, String> applicationNameMap = applicationService.getNameMap(appServiceList.stream()
            .map(IscAppService::getApplicationId).collect(Collectors.toSet()));
        for (IscAppServiceApplyVo vo : list) {
            IscAppService appService = appServiceMap.get(vo.getAppServiceId());
            if(Objects.isNull(appService)) {
                continue;
            }
            vo.setServiceName(serviceNameMap.get(appService.getServiceId()));
            vo.setApplicationName(applicationNameMap.get(appService.getApplicationId()));
        }
    }

    private LambdaQueryWrapper<IscAppServiceApply> buildQueryWrapper(IscAppServiceApplyBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IscAppServiceApply> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getApplyType()), IscAppServiceApply::getApplyType, bo.getApplyType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), IscAppServiceApply::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(IscAppServiceApplyBo bo) {
        IscAppServiceApply add = BeanUtil.toBean(bo, IscAppServiceApply.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(IscAppServiceApplyBo bo) {
        IscAppServiceApply update = BeanUtil.toBean(bo, IscAppServiceApply.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(IscAppServiceApply entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }

    @Override
    public Boolean audit(IscAuditBo bo)
    {
        serviceService.checkAuditBO(bo);
        boolean result = true;
        for (Long id : bo.getIds())
        {
            IscAppServiceApply apply = getOne(Wrappers.<IscAppServiceApply>lambdaQuery()
                    .eq(IscAppServiceApply::getApplyId, id)
                    .eq(IscAppServiceApply::getStatus, IscConstants.AUDIT_WAIT), false);
            if(Objects.isNull(apply)) {
                continue;
            }
            result = updateById(new IscAppServiceApply().setApplyId(id).setStatus(bo.getStatus())
                    .setAuditMind(bo.getRemark()));
            IscAppService appService = appServiceService.getById(apply.getAppServiceId());
            Assert.notNull(appService, () -> new ServiceException("申请信息不存在"));

            IscAppService updateData = null;
            IscService service = null;
            if(IscConstants.AUDIT_PASS.equals(bo.getStatus())) {
                service = serviceService.getById(appService.getServiceId());
                Assert.notNull(service, () -> new ServiceException("服务信息不存在"));
                updateData = genAuditPassData(apply, appService, service, bo.getStatus());
                updateData.setAuditMind(bo.getRemark());
            }else{
                //如果 应用服务 状态为不通过 时需要同步状态
                updateData = genAuditRejectData(appService, bo);
            }
            if(Objects.nonNull(updateData)) {
                result = appServiceService.updateById(updateData);
                if(Objects.nonNull(service)) {
                    IscAppService dbAppService = appServiceService.getById(apply.getAppServiceId());
                    IscApplication application = applicationService.getById(dbAppService.getApplicationId());
                    RouteUtils.saveRule(RouteUtils.generateRule(dbAppService, application.getAccessKey()));
                }
            }
        }
        return result;
    }

    /**
     * 审核通过处理
     * @param apply      申请信息
     * @param appService 应用服务信息
     * @param service    服务信息
     * @param status     操作状态
     * @return 更新数据
     */
    private IscAppService genAuditPassData(IscAppServiceApply apply, IscAppService appService, IscService service,
                                           String status)
    {
        IscAppService updateData = new IscAppService().setAppServiceId(appService.getAppServiceId());
        //如果是 服务申请 修改 应用服务 审核状态、生成虚拟地址、服务到期时间
        //如果是续期申请 修改 服务到期时间
        switch (apply.getApplyType()) {
            case IscConstants.APPLY_TYPE_APPLY:
                genServicePassInit(apply, appService, status, updateData, service);
                break;
            case IscConstants.APPLY_TYPE_MODIFY:
                //如果 应用服务 状态不为通过 则修改状态（如果是通过：续期不能影响使用）
                if(!IscConstants.AUDIT_PASS.equals(appService.getStatus())) {
                    genServicePassInit(apply, appService, status, updateData, service);
                }
                updateData.setQuotaDays(apply.getQuotaDays());
                updateData.setQuotaHours(apply.getQuotaHours());
                updateData.setQuotaMinutes(apply.getQuotaMinutes());
                updateData.setQuotaSeconds(apply.getQuotaSeconds());
                break;
            case IscConstants.APPLY_TYPE_RENEWAL:
                Date startDate = Objects.isNull(appService.getEndTime()) ? DateUtils.getNowDate() : appService.getEndTime();
                updateData.setEndTime(DateUtils.beginOfDay(DateUtils.addMonths(startDate, apply.getRenewalDuration())));
                break;
            default:
                throw new ServiceException("申请类型异常");
        }
        return updateData;
    }

    /**
     * 审核通过 初始化生成 虚拟地址、服务结束时间
     * @param apply      申请信息
     * @param appService 应用服务信息
     * @param status     审核状态
     * @param updateData 需要修改的数据
     * @param service    服务信息
     */
    private void genServicePassInit(IscAppServiceApply apply, IscAppService appService, String status,
                                    IscAppService updateData, IscService service)
    {
        updateData.setStatus(status);
        Integer renewalDuration = apply.getRenewalDuration();
        //如果 申请时长为空，需要查询审核记录 中的审核时长
        if(Objects.isNull(renewalDuration)) {
            IscAppServiceApply log = getOne(Wrappers.<IscAppServiceApply>lambdaQuery()
                .eq(IscAppServiceApply::getAppServiceId, appService.getAppServiceId())
                .eq(IscAppServiceApply::getApplyType, IscConstants.APPLY_TYPE_APPLY)
                .last("LIMIT 1"), false);
            renewalDuration = Objects.nonNull(log) ? log.getRenewalDuration() : 1;
        }
        updateData.setEndTime(DateUtils.beginOfDay(DateUtils.addMonths(DateUtils.getNowDate(), renewalDuration)));
        updateData.setVirtualAddr(RouteUtils.genVirtualAddrPath(service.getServiceAddr()));
    }

    /**
     * 审核不通过处理
     * @param appService 应用服务信息
     * @param bo         审核数据
     * @return 需要更新的信息
     */
    private IscAppService genAuditRejectData(IscAppService appService, IscAuditBo bo) {
        if(!IscConstants.AUDIT_PASS.equals(appService.getStatus())) {
            IscAppService updateData = new IscAppService().setAppServiceId(appService.getAppServiceId());
            updateData.setStatus(bo.getStatus());
            updateData.setAuditMind(bo.getRemark());
            return updateData;
        }
        return null;
    }
}
