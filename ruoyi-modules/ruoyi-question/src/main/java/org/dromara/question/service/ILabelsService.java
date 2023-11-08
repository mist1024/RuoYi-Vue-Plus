package org.dromara.question.service;

import org.dromara.question.domain.Labels;
import org.dromara.question.domain.vo.LabelsVo;
import org.dromara.question.domain.bo.LabelsBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 题目标签Service接口
 *
 * @author Lion Li
 * @date 2023-11-08
 */
public interface ILabelsService {

    /**
     * 查询题目标签
     */
    LabelsVo queryById(Long id);

    /**
     * 查询题目标签列表
     */
    TableDataInfo<LabelsVo> queryPageList(LabelsBo bo, PageQuery pageQuery);

    /**
     * 查询题目标签列表
     */
    List<LabelsVo> queryList(LabelsBo bo);

    /**
     * 新增题目标签
     */
    Boolean insertByBo(LabelsBo bo);

    /**
     * 修改题目标签
     */
    Boolean updateByBo(LabelsBo bo);

    /**
     * 校验并批量删除题目标签信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
