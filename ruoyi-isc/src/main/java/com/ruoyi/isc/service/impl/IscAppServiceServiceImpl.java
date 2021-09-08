package com.ruoyi.isc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.isc.domain.bo.IscAppServiceBo;
import com.ruoyi.isc.domain.vo.IscAppServiceVo;
import com.ruoyi.isc.domain.IscAppService;
import com.ruoyi.isc.mapper.IscAppServiceMapper;
import com.ruoyi.isc.service.IIscAppServiceService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 应用服务Service业务层处理
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */
@Service
public class IscAppServiceServiceImpl extends ServicePlusImpl<IscAppServiceMapper, IscAppService, IscAppServiceVo> implements IIscAppServiceService {

    @Override
    public IscAppServiceVo queryById(Long serviceAppId){
        return getVoById(serviceAppId);
    }

    @Override
    public TableDataInfo<IscAppServiceVo> queryPageList(IscAppServiceBo bo) {
        PagePlus<IscAppService, IscAppServiceVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<IscAppServiceVo> queryList(IscAppServiceBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<IscAppService> buildQueryWrapper(IscAppServiceBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IscAppService> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getServiceId() != null, IscAppService::getServiceId, bo.getServiceId());
        lqw.eq(bo.getApplicationId() != null, IscAppService::getApplicationId, bo.getApplicationId());
        lqw.eq(bo.getUserId() != null, IscAppService::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getEnabled()), IscAppService::getEnabled, bo.getEnabled());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), IscAppService::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(IscAppServiceBo bo) {
        IscAppService add = BeanUtil.toBean(bo, IscAppService.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(IscAppServiceBo bo) {
        IscAppService update = BeanUtil.toBean(bo, IscAppService.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(IscAppService entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }
}
