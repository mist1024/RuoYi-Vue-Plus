package org.dromara.imok.domain.bo;

import org.dromara.imok.domain.TrainingCourses;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 培训课程管理业务对象 training_courses
 *
 * @author sungk
 * @date 2024-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TrainingCourses.class, reverseConvertGenerate = false)
public class TrainingCoursesBo extends BaseEntity {

    /**
     * 主健
     */
    @NotBlank(message = "主健不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 课程名称
     */
    @NotBlank(message = "课程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 课程类别
     */
    @NotBlank(message = "课程类别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String category;

    /**
     * 课程介绍
     */
    @NotBlank(message = "课程介绍不能为空", groups = { AddGroup.class, EditGroup.class })
    private String description;

    /**
     * 课程大纲
     */
    @NotBlank(message = "课程大纲不能为空", groups = { AddGroup.class, EditGroup.class })
    private String syllabus;

    /**
     * 授课讲师
     */
    @NotBlank(message = "授课讲师不能为空", groups = { AddGroup.class, EditGroup.class })
    private String instructor;

    /**
     * 讲师简介
     */
    @NotBlank(message = "讲师简介不能为空", groups = { AddGroup.class, EditGroup.class })
    private String instructorProfile;

    /**
     * 开课日期
     */
    @NotNull(message = "开课日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date startDate;

    /**
     * 结课日期
     */
    @NotNull(message = "结课日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date endDate;

    /**
     * 上课地点
     */
    @NotBlank(message = "上课地点不能为空", groups = { AddGroup.class, EditGroup.class })
    private String location;

    /**
     * 课程状态
     */
    @NotBlank(message = "课程状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 创建时间
     */
    @NotNull(message = "创建时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date createdAt;

    /**
     * 更新时间
     */
    @NotNull(message = "更新时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date updatedAt;

    /**
     * 课程编号
     */
    @NotBlank(message = "课程编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String courseId;


}
