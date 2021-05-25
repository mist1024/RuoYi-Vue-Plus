package com.ruoyi.edu.bo;

import com.ruoyi.edu.domain.EduStudent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 学生考试信息编辑对象 edu_student_exam
 *
 * @author keyleaf
 * @date 2021-05-23
 */
@Data
@ApiModel("学生考试信息编辑对象")
public class EduStudentExamEditBo {


    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private Long id;

    /** 学生id */
    @ApiModelProperty("学生id")
    @NotNull(message = "学生id不能为空")
    private Long studentId;

    /** 考试id */
    @ApiModelProperty("考试id")
    @NotNull(message = "考试id不能为空")
    private Long examId;

    /** 语文分数 */
    @ApiModelProperty("语文分数")
    private BigDecimal chineseScore;

    /** 数学分数 */
    @ApiModelProperty("数学分数")
    private BigDecimal mathScore;

    /** 英语分数 */
    @ApiModelProperty("英语分数")
    private BigDecimal englishScore;

    /** 化学分数 */
    @ApiModelProperty("化学分数")
    private BigDecimal chemistryScore;

    /** 物理分数 */
    @ApiModelProperty("物理分数")
    private BigDecimal physicsScore;

    /** 生物分数 */
    @ApiModelProperty("生物分数")
    private BigDecimal biologyScore;

    /** 政治分数 */
    @ApiModelProperty("政治分数")
    private BigDecimal politicsScore;

    /** 历史分数 */
    @ApiModelProperty("历史分数")
    private BigDecimal historyScore;

    /** 地理分数 */
    @ApiModelProperty("地理分数")
    private BigDecimal geographyScore;

    /** 思想品德分数 */
    @ApiModelProperty("思想品德分数")
    private BigDecimal ethicScore;

    /** 班级排名 */
    @ApiModelProperty("班级排名")
    private Long classRank;

    /** 级部排名 */
    @ApiModelProperty("级部排名")
    private Long gradeRank;

    /** 学生信息信息 */
    @ApiModelProperty("学生信息")
    private List<EduStudent> eduStudentList;
}
