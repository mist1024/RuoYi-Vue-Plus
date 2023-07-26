package com.ruoyi.work.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.work.domain.bo.ProcessBo;
import com.ruoyi.work.domain.vo.ProcessVo;

import java.util.Collection;
import java.util.List;

/**
 * 流程Service接口
 *
 * @author ruoyi
 * @date 2023-02-03
 */
public interface IProcessService {

    /**
     * 查询流程
     */
    ProcessVo queryById(Long id);

    /**
     * 查询流程列表
     */
    TableDataInfo<ProcessVo> queryPageList(ProcessBo bo, PageQuery pageQuery);

    /**
     * 查询流程列表
     */
    List<ProcessVo> queryList(ProcessBo bo);

    /**
     * 新增流程
     */
    Boolean insertByBo(ProcessBo bo);

    /**
     * 修改流程
     */
    Boolean updateByBo(ProcessBo bo);

    /**
     * 校验并批量删除流程信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
