package com.ruoyi.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.demo.domain.bo.HjcSelectionBo;
import com.ruoyi.demo.domain.vo.HjcSelectionVo;
import com.ruoyi.demo.domain.HjcSelection;
import com.ruoyi.demo.mapper.HjcSelectionMapper;
import com.ruoyi.demo.service.IHjcSelectionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 图片选择参数Service业务层处理
 *
 * @author qianlan
 * @date 2021-10-12
 */
@Service
public class HjcSelectionServiceImpl extends ServicePlusImpl<HjcSelectionMapper, HjcSelection, HjcSelectionVo> implements IHjcSelectionService {

    @Override
    public HjcSelectionVo queryById(Long sid){
        return getVoById(sid);
    }

    @Override
    public TableDataInfo<HjcSelectionVo> queryPageList(HjcSelectionBo bo) {
        PagePlus<HjcSelection, HjcSelectionVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<HjcSelectionVo> queryList(HjcSelectionBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<HjcSelection> buildQueryWrapper(HjcSelectionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HjcSelection> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getPid() != null, HjcSelection::getPid, bo.getPid());
        lqw.eq(bo.getSelection() != null, HjcSelection::getSelection, bo.getSelection());
        return lqw;
    }

    @Override
    public Boolean insertByBo(HjcSelectionBo bo) {
        HjcSelection add = BeanUtil.toBean(bo, HjcSelection.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(HjcSelectionBo bo) {
        HjcSelection update = BeanUtil.toBean(bo, HjcSelection.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(HjcSelection entity){
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
