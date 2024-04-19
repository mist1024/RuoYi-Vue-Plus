package org.dromara.imok.service;

import org.dromara.imok.domain.TrainingCourses;
import org.dromara.imok.domain.vo.TrainingCoursesVo;
import org.dromara.imok.domain.bo.TrainingCoursesBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 培训课程管理Service接口
 *
 * @author sungk
 * @date 2024-04-19
 */
public interface ITrainingCoursesService {

    /**
     * 查询培训课程管理
     */
    TrainingCoursesVo queryById(String id);

    /**
     * 查询培训课程管理列表
     */
    TableDataInfo<TrainingCoursesVo> queryPageList(TrainingCoursesBo bo, PageQuery pageQuery);

    /**
     * 查询培训课程管理列表
     */
    List<TrainingCoursesVo> queryList(TrainingCoursesBo bo);

    /**
     * 新增培训课程管理
     */
    Boolean insertByBo(TrainingCoursesBo bo);

    /**
     * 修改培训课程管理
     */
    Boolean updateByBo(TrainingCoursesBo bo);

    /**
     * 校验并批量删除培训课程管理信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
