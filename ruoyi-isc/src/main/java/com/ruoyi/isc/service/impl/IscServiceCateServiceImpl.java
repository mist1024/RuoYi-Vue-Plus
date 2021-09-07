package com.ruoyi.isc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.FullPathUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.isc.domain.bo.IscServiceCateBo;
import com.ruoyi.isc.domain.vo.IscServiceCateVo;
import com.ruoyi.isc.domain.IscServiceCate;
import com.ruoyi.isc.mapper.IscServiceCateMapper;
import com.ruoyi.isc.service.IIscServiceCateService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务分类Service业务层处理
 *
 * @author wenchao gong
 * @date 2021-08-22
 */
@Service
public class IscServiceCateServiceImpl extends ServicePlusImpl<IscServiceCateMapper, IscServiceCate, IscServiceCateVo> implements IIscServiceCateService {

    @Override
    public IscServiceCateVo queryById(Long cateId){
        return getVoById(cateId);
    }


    @Override
    public List<IscServiceCateVo> queryList(IscServiceCateBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<IscServiceCate> buildQueryWrapper(IscServiceCateBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IscServiceCate> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCateName()), IscServiceCate::getCateName, bo.getCateName());
        lqw.eq(StringUtils.isNotBlank(bo.getEnabled()), IscServiceCate::getEnabled, bo.getEnabled());
        return lqw;
    }

    @Override
    public Boolean insertByBo(IscServiceCateBo bo) {
        IscServiceCate add = BeanUtil.toBean(bo, IscServiceCate.class);
        validEntityBeforeSave(add);
        add.setFullPath(getFullPath(bo.getParentId()));
        return save(add);
    }

    /**
     * 通过父ID生成 FULL_PATH
     * @param parentId
     * @return
     */
    private String getFullPath(Long parentId)
    {
        final boolean isRoot = parentId == 0L;
        IscServiceCate cate = getOne(Wrappers.<IscServiceCate>lambdaQuery()
                .select(IscServiceCate::getCateId, IscServiceCate::getFullPath)
                .eq(isRoot, IscServiceCate::getParentId, 0L).and(!isRoot, w -> w
                        .eq(IscServiceCate::getParentId, parentId)
                        .or()
                        .eq(IscServiceCate::getCateId, parentId))
                .orderByDesc(IscServiceCate::getFullPath)
                .last("LIMIT 1"));
        String parentFullPath = null;
        String maxFullPath = null;
        if(Objects.nonNull(cate)){
            if(cate.getCateId().intValue() == parentId)
            {
                parentFullPath = cate.getFullPath();
            }else{
                maxFullPath = cate.getFullPath();
            }
        }
        final String fullPath = FullPathUtils.genFullPath(parentFullPath, maxFullPath);
        return fullPath;
    }

    @Override
    public Boolean updateByBo(IscServiceCateBo bo) {
        IscServiceCate update = BeanUtil.toBean(bo, IscServiceCate.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(IscServiceCate entity){
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
    public List<IscServiceCate> selectCateList()
    {
        return list(Wrappers.<IscServiceCate>lambdaQuery()
                .eq(IscServiceCate::getEnabled, UserConstants.DICT_NORMAL)
                .orderByAsc(IscServiceCate::getParentId)
                .orderByAsc(IscServiceCate::getOrderNum));
    }

    @Override
    public Map<String, String> batchCateName(Set<String> fullPathList)
    {
        Map<String, String> results = new HashMap<>();
        if(CollectionUtils.isEmpty(fullPathList)) {
            return results;
        }
        final List<IscServiceCate> list = list(Wrappers.<IscServiceCate>lambdaQuery()
                .select(IscServiceCate::getCateId, IscServiceCate::getCateName, IscServiceCate::getFullPath, IscServiceCate::getParentId)
                .in(IscServiceCate::getFullPath, fullPathList));
        Map<Long, String> cateNameMap = batchCateName(list);
        for (IscServiceCate cate : list)
        {
            results.put(cate.getFullPath(), cateNameMap.get(cate.getCateId()));
        }
        return results;
    }

    private Map<Long, String> batchCateName(List<IscServiceCate> list) {
        Map<Long, String> results = new HashMap<>();
        final Set<Long> parentIds = list.stream().filter(m -> m.getParentId() != 0L).map(IscServiceCate::getParentId).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(parentIds)) {
            final List<IscServiceCate> parentList = list(Wrappers.<IscServiceCate>lambdaQuery()
                    .select(IscServiceCate::getCateId, IscServiceCate::getCateName, IscServiceCate::getFullPath, IscServiceCate::getParentId)
                    .in(IscServiceCate::getCateId, parentIds));
            results.putAll(batchCateName(parentList));
        }
        for (IscServiceCate cate : list)
        {
            final String parentName = results.get(cate.getParentId());
            if(StringUtils.isBlank(parentName)) {
                results.put(cate.getCateId(), cate.getCateName());
            }else{
                results.put(cate.getCateId(), String.format("%s/%s", parentName, cate.getCateName()));
            }
        }
        return results;
    }
}
