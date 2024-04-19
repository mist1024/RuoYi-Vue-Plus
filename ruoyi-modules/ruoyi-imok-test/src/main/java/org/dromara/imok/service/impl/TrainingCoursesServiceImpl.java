package org.dromara.imok.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import jakarta.annotation.Resource;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.imok.domain.bo.TrainingCoursesBo;
import org.dromara.imok.domain.vo.TrainingCoursesVo;
import org.dromara.imok.domain.TrainingCourses;
import org.dromara.imok.mapper.TrainingCoursesMapper;
import org.dromara.imok.service.ITrainingCoursesService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 培训课程管理Service业务层处理
 *
 * @author sungk
 * @date 2024-04-19
 */
@RequiredArgsConstructor
@Service
@DS("oracle")
public class TrainingCoursesServiceImpl implements ITrainingCoursesService {

    @Resource
    private final TrainingCoursesMapper baseMapper;

    /**
     * 查询培训课程管理
     */
    @Override
    public TrainingCoursesVo queryById(String id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询培训课程管理列表
     */
    @Override
    public TableDataInfo<TrainingCoursesVo> queryPageList(TrainingCoursesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TrainingCourses> lqw = buildQueryWrapper(bo);
        Page<TrainingCoursesVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询培训课程管理列表
     */
    @Override
    public List<TrainingCoursesVo> queryList(TrainingCoursesBo bo) {
        LambdaQueryWrapper<TrainingCourses> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TrainingCourses> buildQueryWrapper(TrainingCoursesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TrainingCourses> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), TrainingCourses::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getCategory()), TrainingCourses::getCategory, bo.getCategory());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), TrainingCourses::getDescription, bo.getDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getSyllabus()), TrainingCourses::getSyllabus, bo.getSyllabus());
        lqw.eq(StringUtils.isNotBlank(bo.getInstructor()), TrainingCourses::getInstructor, bo.getInstructor());
        lqw.eq(StringUtils.isNotBlank(bo.getInstructorProfile()), TrainingCourses::getInstructorProfile, bo.getInstructorProfile());
        lqw.eq(bo.getStartDate() != null, TrainingCourses::getStartDate, bo.getStartDate());
        lqw.eq(bo.getEndDate() != null, TrainingCourses::getEndDate, bo.getEndDate());
        lqw.eq(StringUtils.isNotBlank(bo.getLocation()), TrainingCourses::getLocation, bo.getLocation());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), TrainingCourses::getStatus, bo.getStatus());
        lqw.eq(bo.getCreatedAt() != null, TrainingCourses::getCreatedAt, bo.getCreatedAt());
        lqw.eq(bo.getUpdatedAt() != null, TrainingCourses::getUpdatedAt, bo.getUpdatedAt());
        lqw.eq(StringUtils.isNotBlank(bo.getCourseId()), TrainingCourses::getCourseId, bo.getCourseId());
        return lqw;
    }

    /**
     * 新增培训课程管理
     */
    @Override
    public Boolean insertByBo(TrainingCoursesBo bo) {
        TrainingCourses add = MapstructUtils.convert(bo, TrainingCourses.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改培训课程管理
     */
    @Override
    public Boolean updateByBo(TrainingCoursesBo bo) {
        TrainingCourses update = MapstructUtils.convert(bo, TrainingCourses.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TrainingCourses entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除培训课程管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
