package org.dromara.imok.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.imok.domain.TrainingCourses;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 培训课程管理视图对象 training_courses
 *
 * @author sungk
 * @date 2024-04-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TrainingCourses.class)
public class TrainingCoursesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主健
     */
    @ExcelProperty(value = "主健")
    private String id;

    /**
     * 课程名称
     */
    @ExcelProperty(value = "课程名称")
    private String name;

    /**
     * 课程类别
     */
    @ExcelProperty(value = "课程类别")
    private String category;

    /**
     * 课程介绍
     */
    @ExcelProperty(value = "课程介绍")
    private String description;

    /**
     * 课程大纲
     */
    @ExcelProperty(value = "课程大纲")
    private String syllabus;

    /**
     * 授课讲师
     */
    @ExcelProperty(value = "授课讲师")
    private String instructor;

    /**
     * 讲师简介
     */
    @ExcelProperty(value = "讲师简介")
    private String instructorProfile;

    /**
     * 开课日期
     */
    @ExcelProperty(value = "开课日期")
    private Date startDate;

    /**
     * 结课日期
     */
    @ExcelProperty(value = "结课日期")
    private Date endDate;

    /**
     * 上课地点
     */
    @ExcelProperty(value = "上课地点")
    private String location;

    /**
     * 课程状态
     */
    @ExcelProperty(value = "课程状态")
    private String status;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createdAt;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updatedAt;

    /**
     * 课程编号
     */
    @ExcelProperty(value = "课程编号")
    private String courseId;


}
