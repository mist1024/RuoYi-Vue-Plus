package org.dromara.imok.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 培训课程管理对象 training_courses
 *
 * @author sungk
 * @date 2024-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("training_courses")
public class TrainingCourses extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主健
     */
    @TableId(value = "id")
    private String id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程类别
     */
    private String category;

    /**
     * 课程介绍
     */
    private String description;

    /**
     * 课程大纲
     */
    private String syllabus;

    /**
     * 授课讲师
     */
    private String instructor;

    /**
     * 讲师简介
     */
    private String instructorProfile;

    /**
     * 开课日期
     */
    private Date startDate;

    /**
     * 结课日期
     */
    private Date endDate;

    /**
     * 上课地点
     */
    private String location;

    /**
     * 课程状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 课程编号
     */
    private String courseId;


}
