package com.ruoyi.edu.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import javax.validation.constraints.*;


/**
 * 学生信息编辑对象 edu_student
 *
 * @author keyleaf
 * @date 2021-05-22
 */
@Data
@ApiModel("学生信息编辑对象")
public class EduStudentEditBo {


    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private Long id;

    /** 身份证号 */
    @ApiModelProperty("身份证号")
    @NotBlank(message = "身份证号不能为空")
    private String idCardNo;

    /** 学生姓名 */
    @ApiModelProperty("学生姓名")
    @NotBlank(message = "学生姓名不能为空")
    private String studentName;

    /** 年级 */
    @ApiModelProperty("年级")
    private String currentGrade;

    /** 班级 */
    @ApiModelProperty("班级")
    private String currentClass;

    /** 学校 */
    @ApiModelProperty("学校")
    private String currentSchool;

    /** 手机号 */
    @ApiModelProperty("手机号")
    private String mobile;

    /** 父亲手机 */
    @ApiModelProperty("父亲手机")
    private String fatherMobile;

    /** 母亲手机 */
    @ApiModelProperty("母亲手机")
    private String motherMobile;
}
