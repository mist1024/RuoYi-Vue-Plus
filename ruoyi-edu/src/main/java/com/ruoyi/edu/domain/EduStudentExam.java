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
 * 学生考试信息对象 edu_student_exam
 * 
 * @author keyleaf
 * @date 2021-05-23
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("edu_student_exam")
public class EduStudentExam implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 学生id */
    private Long studentId;

    /** 考试id */
    private Long examId;

    /** 语文分数 */
    private BigDecimal chineseScore;

    /** 数学分数 */
    private BigDecimal mathScore;

    /** 英语分数 */
    private BigDecimal englishScore;

    /** 化学分数 */
    private BigDecimal chemistryScore;

    /** 物理分数 */
    private BigDecimal physicsScore;

    /** 生物分数 */
    private BigDecimal biologyScore;

    /** 政治分数 */
    private BigDecimal politicsScore;

    /** 历史分数 */
    private BigDecimal historyScore;

    /** 地理分数 */
    private BigDecimal geographyScore;

    /** 思想品德分数 */
    private BigDecimal ethicScore;

    /** 班级排名 */
    private Long classRank;

    /** 级部排名 */
    private Long gradeRank;

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
