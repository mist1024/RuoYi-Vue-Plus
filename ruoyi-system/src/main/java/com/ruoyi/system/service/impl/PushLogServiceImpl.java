package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.domain.event.PushLogEvent;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.PushLogBo;
import com.ruoyi.system.domain.vo.PushLogVo;
import com.ruoyi.system.domain.PushLog;
import com.ruoyi.system.mapper.PushLogMapper;
import com.ruoyi.system.service.IPushLogService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 推送日志Service业务层处理
 *
 * @author ruoyi
 * @date 2023-07-20
 */
@RequiredArgsConstructor
@Service
public class PushLogServiceImpl implements IPushLogService {

    private final PushLogMapper baseMapper;

    /**
     * 查询推送日志
     */
    @Override
    public PushLogVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询推送日志列表
     */
    @Override
    public TableDataInfo<PushLogVo> queryPageList(PushLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PushLog> lqw = buildQueryWrapper(bo);
        Page<PushLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询推送日志列表
     */
    @Override
    public List<PushLogVo> queryList(PushLogBo bo) {
        LambdaQueryWrapper<PushLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PushLog> buildQueryWrapper(PushLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<PushLog> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPushData()), PushLog::getPushData, bo.getPushData());
        lqw.eq(StringUtils.isNotBlank(bo.getResultData()), PushLog::getResultData, bo.getResultData());
        return lqw;
    }

    /**
     * 新增推送日志
     */
    @Async
    @EventListener
    public void insertByBo(PushLogEvent event) {
        PushLog add = BeanUtil.toBean(event, PushLog.class);
        baseMapper.insert(add);
    }

    /**
     * 修改推送日志
     */
    @Override
    public Boolean updateByBo(PushLogBo bo) {
        PushLog update = BeanUtil.toBean(bo, PushLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(PushLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除推送日志
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
