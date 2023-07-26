package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.event.PushLogEvent;
import com.ruoyi.system.domain.vo.PushLogVo;
import com.ruoyi.system.domain.bo.PushLogBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 推送日志Service接口
 *
 * @author ruoyi
 * @date 2023-07-20
 */
public interface IPushLogService {

    /**
     * 查询推送日志
     */
    PushLogVo queryById(Long id);

    /**
     * 查询推送日志列表
     */
    TableDataInfo<PushLogVo> queryPageList(PushLogBo bo, PageQuery pageQuery);

    /**
     * 查询推送日志列表
     */
    List<PushLogVo> queryList(PushLogBo bo);


    /**
     * 修改推送日志
     */
    Boolean updateByBo(PushLogBo bo);

    /**
     * 校验并批量删除推送日志信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
