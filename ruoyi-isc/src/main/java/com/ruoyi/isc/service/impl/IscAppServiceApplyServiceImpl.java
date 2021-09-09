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
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.isc.domain.IscAppService;
import com.ruoyi.isc.domain.IscAppServiceApply;
import com.ruoyi.isc.domain.bo.IscAppServiceApplyBo;
import com.ruoyi.isc.domain.bo.IscAuditBo;
import com.ruoyi.isc.domain.vo.IscAppServiceApplyVo;
import com.ruoyi.isc.mapper.IscAppServiceApplyMapper;
import com.ruoyi.isc.service.IIscAppServiceApplyService;
import com.ruoyi.isc.service.IIscAppServiceService;
import com.ruoyi.isc.service.IIscApplicationService;
import com.ruoyi.isc.service.IIscServiceService;
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
        switch (bo.getStatus()) {
            case IscConstants.AUDIT_PASS:
                break;
            case IscConstants.AUDIT_REJECT:
                Assert.notBlank(bo.getRemark(), () -> new ServiceException("审核意见不能为空"));
                break;
            default:
                throw new ServiceException("审核状态异常");
        }
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
        }
        return result;
    }
}
