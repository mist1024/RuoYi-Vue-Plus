package org.dromara.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.question.domain.bo.LabelsBo;
import org.dromara.question.domain.vo.LabelsVo;
import org.dromara.question.domain.Labels;
import org.dromara.question.mapper.LabelsMapper;
import org.dromara.question.service.ILabelsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 题目标签Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@RequiredArgsConstructor
@Service
public class LabelsServiceImpl implements ILabelsService {

    private final LabelsMapper baseMapper;

    /**
     * 查询题目标签
     */
    @Override
    public LabelsVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询题目标签列表
     */
    @Override
    public TableDataInfo<LabelsVo> queryPageList(LabelsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Labels> lqw = buildQueryWrapper(bo);
        Page<LabelsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询题目标签列表
     */
    @Override
    public List<LabelsVo> queryList(LabelsBo bo) {
        LambdaQueryWrapper<Labels> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Labels> buildQueryWrapper(LabelsBo bo) {
        LambdaQueryWrapper<Labels> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getLabel()), Labels::getLabel, bo.getLabel());
        lqw.eq(bo.getStatus() != null, Labels::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增题目标签
     */
    @Override
    public Boolean insertByBo(LabelsBo bo) {
        Labels add = MapstructUtils.convert(bo, Labels.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改题目标签
     */
    @Override
    public Boolean updateByBo(LabelsBo bo) {
        Labels update = MapstructUtils.convert(bo, Labels.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    @Override
    public Boolean updateStatusByBo(LabelsBo bo) {
        Labels update = MapstructUtils.convert(bo, Labels.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Labels entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除题目标签
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
