package org.dromara.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.question.domain.Options;
import org.dromara.question.domain.Title;
import org.dromara.question.domain.bo.TitleBo;
import org.dromara.question.domain.bo.TitleResp;
import org.dromara.question.domain.vo.LabelsVo;
import org.dromara.question.domain.vo.OptionVo;
import org.dromara.question.domain.vo.TitleVo;
import org.dromara.question.mapper.LabelsMapper;
import org.dromara.question.mapper.OptionMapper;
import org.dromara.question.mapper.TitleMapper;
import org.dromara.question.service.ITitleService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 题目Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@RequiredArgsConstructor
@Service
public class TitleServiceImpl implements ITitleService {

    private final TitleMapper baseMapper;

    private final OptionMapper optionMapper;

    private final LabelsMapper labelsMapper;

    /**
     * 查询题目
     */
    @Override
    public TitleResp queryById(Long id) {
        TitleVo titleVo = baseMapper.selectVoById(id);
        List<OptionVo> options = optionMapper.selectVoList(new LambdaQueryWrapper<Options>()
            .eq(Options::getQuestionId, titleVo.getId()));

        TitleResp resp = new TitleResp();
        resp.setOptionVoList(options);
        return resp;
    }

    /**
     * 查询题目列表
     */
    @Override
    public TableDataInfo<TitleResp> queryPageList(TitleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Title> lqw = buildQueryWrapper(bo);
        Page<TitleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        // 获取标签id，获取选项id
        List<TitleVo> records = result.getRecords();
        List<Long> labels = records.stream().map(TitleVo::getLabelId).collect(Collectors.toList());
        List<LabelsVo> labelsVos = labelsMapper.selectVoBatchIds(labels);
        Map<Long, List<LabelsVo>> labelsVoMap = labelsVos.stream().collect(Collectors.groupingBy(LabelsVo::getId));
        List<Long> recordIds = records.stream().map(TitleVo::getId).collect(Collectors.toList());

        List<OptionVo> optionVos = optionMapper.selectVoList(new LambdaQueryWrapper<Options>()
            .in(Options::getQuestionId, recordIds));

        Map<Long, List<OptionVo>> optionsMap = optionVos.stream().collect(Collectors.groupingBy(OptionVo::getQuestionId));

        List<TitleResp> resp = records.stream().map(p -> {
            TitleResp dto = new TitleResp();
            dto.setId(p.getId());
            dto.setQuestion(p.getQuestion());
            List<LabelsVo> labelsVos1 = labelsVoMap.get(p.getLabelId());
            dto.setLabelName(labelsVos1.get(0).getLabel());
            List<OptionVo> optionVos1 = optionsMap.get(p.getId());
            dto.setOptionVoList(optionVos1);
            return dto;
        }).collect(Collectors.toList());

        Page<TitleResp> respPage = new Page<>();
        respPage.setRecords(resp);
        respPage.setCurrent(pageQuery.getPageNum());
        respPage.setTotal(result.getTotal());
        respPage.setSize(result.getSize());

        return TableDataInfo.build(respPage);
    }

    /**
     * 查询题目列表
     */
    @Override
    public List<TitleVo> queryList(TitleBo bo) {
        LambdaQueryWrapper<Title> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Title> buildQueryWrapper(TitleBo bo) {
        LambdaQueryWrapper<Title> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getQuestion()), Title::getQuestion, bo.getQuestion());
        lqw.eq(bo.getLabelId() != null, Title::getLabelId, bo.getLabelId());
        return lqw;
    }

    /**
     * 新增题目
     */
    @Override
    public Boolean insertByBo(TitleBo bo) {
        Title add = MapstructUtils.convert(bo, Title.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改题目
     */
    @Override
    public Boolean updateByBo(TitleBo bo) {
        Title update = MapstructUtils.convert(bo, Title.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Title entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除题目
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
