package com.ruoyi.work.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.work.domain.TProcess;
import com.ruoyi.work.domain.bo.ProcessBo;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.mapper.ProcessMapper;
import com.ruoyi.work.service.IProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 流程Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-03
 */
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class ProcessServiceImpl implements IProcessService {

    private final ProcessMapper baseMapper;



    /**
     * 查询流程
     */
    @Override
    public ProcessVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询流程列表
     */
    @Override
    public TableDataInfo<ProcessVo> queryPageList(ProcessBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TProcess> lqw = buildQueryWrapper(bo);
        Page<ProcessVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询流程列表
     */
    @Override
    public List<ProcessVo> queryList(ProcessBo bo) {
        LambdaQueryWrapper<TProcess> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TProcess> buildQueryWrapper(ProcessBo bo) {
        LambdaQueryWrapper<TProcess> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), TProcess::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getProcessKey()), TProcess::getProcessKey, bo.getProcessKey());
        lqw.eq(StringUtils.isNotBlank(bo.getStep()), TProcess::getStep, bo.getStep());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), TProcess::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getCheckType()), TProcess::getCheckType, bo.getCheckType());
        lqw.eq(StringUtils.isNotBlank(bo.getProcessCheck().toString()), TProcess::getProcessCheck, bo.getProcessCheck());
        lqw.eq(StringUtils.isNotBlank(bo.getCc()), TProcess::getCc, bo.getCc());
        lqw.eq(bo.getIsNext() != null, TProcess::getIsNext, bo.getIsNext());
        return lqw;
    }

    /**
     * 新增流程
     */
    @Override
    public Boolean insertByBo(ProcessBo bo) {
        TProcess add = BeanUtil.toBean(bo, TProcess.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改流程
     */
    @Override
    public Boolean updateByBo(ProcessBo bo) {
        TProcess update = BeanUtil.toBean(bo, TProcess.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TProcess entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除流程
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 通过流程表将人员添加到运行流程表中
     */


}
