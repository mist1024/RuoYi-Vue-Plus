package com.ruoyi.isc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.isc.domain.IscApplication;
import com.ruoyi.isc.domain.bo.IscApplicationBo;
import com.ruoyi.isc.domain.vo.IscApplicationVo;
import com.ruoyi.isc.mapper.IscApplicationMapper;
import com.ruoyi.isc.service.IIscApplicationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 应用信息Service业务层处理
 *
 * @author wenchao gong
 * @date 2021-09-08
 */
@Service
public class IscApplicationServiceImpl extends ServicePlusImpl<IscApplicationMapper, IscApplication, IscApplicationVo> implements IIscApplicationService {

    @Override
    public IscApplicationVo queryById(Long applicationId){
        return getVoById(applicationId);
    }

    @Override
    public TableDataInfo<IscApplicationVo> queryPageList(IscApplicationBo bo) {
        PagePlus<IscApplication, IscApplicationVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<IscApplicationVo> queryList(IscApplicationBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<IscApplication> buildQueryWrapper(IscApplicationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IscApplication> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getApplicationName()), IscApplication::getApplicationName, bo.getApplicationName());
        lqw.eq(StringUtils.isNotBlank(bo.getAccessKey()), IscApplication::getAccessKey, bo.getAccessKey());
        return lqw;
    }

    @Override
    public Boolean insertByBo(IscApplicationBo bo) {
        IscApplication add = BeanUtil.toBean(bo, IscApplication.class);
        validEntityBeforeSave(add);
        add.setAccessKey(IdUtil.simpleUUID());
        add.setUserId(SecurityUtils.getUserId());
        return save(add);
    }

    @Override
    public Boolean updateByBo(IscApplicationBo bo) {
        IscApplication update = BeanUtil.toBean(bo, IscApplication.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(IscApplication entity){
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
