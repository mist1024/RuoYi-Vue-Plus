package com.ruoyi.edu.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;

/**
 * 学生信息对象 edu_student
 *
 * @author keyleaf
 * @date 2021-05-22
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("edu_student")
public class EduStudent implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 身份证号 */
    private String idCardNo;

    /** 学生姓名 */
    private String studentName;

    /** 年级 */
    private String currentGrade;

    /** 班级 */
    private String currentClass;

    /** 学校 */
    private String currentSchool;

    /** 手机号 */
    private String mobile;

    /** 父亲手机 */
    private String fatherMobile;

    /** 母亲手机 */
    private String motherMobile;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人id */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 修改时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 修改人id */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
