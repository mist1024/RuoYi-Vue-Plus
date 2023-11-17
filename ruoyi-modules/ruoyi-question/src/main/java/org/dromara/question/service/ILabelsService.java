package org.dromara.question.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.question.domain.bo.LabelsBo;
import org.dromara.question.domain.vo.LabelOptionVo;
import org.dromara.question.domain.vo.LabelsVo;

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
    Boolean updateStatusByBo(LabelsBo bo);

    List<LabelOptionVo> queryLabels();

    /**
     * 校验并批量删除题目标签信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
