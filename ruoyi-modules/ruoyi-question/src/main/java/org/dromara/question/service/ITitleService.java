package org.dromara.question.service;

import org.dromara.question.domain.bo.TitleReq;
import org.dromara.question.domain.bo.TitleResp;
import org.dromara.question.domain.vo.TitleVo;
import org.dromara.question.domain.bo.TitleBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 题目Service接口
 *
 * @author Lion Li
 * @date 2023-11-08
 */
public interface ITitleService {

    /**
     * 查询题目
     */
    TitleResp queryById(Long id);

    /**
     * 查询题目列表
     */
    TableDataInfo<TitleResp> queryPageList(TitleBo bo, PageQuery pageQuery);

    /**
     * 查询题目列表
     */
    List<TitleVo> queryList(TitleBo bo);

    /**
     * 新增题目
     */
    Boolean insertByBo(TitleReq bo);

    /**
     * 修改题目
     */
    Boolean updateByBo(TitleBo bo);

    /**
     * 校验并批量删除题目信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
