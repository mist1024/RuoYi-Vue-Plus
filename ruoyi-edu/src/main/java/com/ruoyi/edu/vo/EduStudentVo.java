package com.ruoyi.edu.vo;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 学生信息视图对象 mall_package
 *
 * @author keyleaf
 * @date 2021-05-22
 */
@Data
@ApiModel("学生信息视图对象")
public class EduStudentVo {
	private static final long serialVersionUID = 1L;

	/** $pkColumn.columnComment */
	@ApiModelProperty("$pkColumn.columnComment")
	private Long id;

	/** 身份证号 */
	@Excel(name = "身份证号")
	@ApiModelProperty("身份证号")
	private String idCardNo;
	/** 学生姓名 */
	@Excel(name = "学生姓名")
	@ApiModelProperty("学生姓名")
	private String studentName;
	/** 年级 */
	@Excel(name = "年级")
	@ApiModelProperty("年级")
	private String currentGrade;
	/** 班级 */
	@Excel(name = "班级")
	@ApiModelProperty("班级")
	private String currentClass;
	/** 学校 */
	@Excel(name = "学校")
	@ApiModelProperty("学校")
	private String currentSchool;
	/** 手机号 */
	@Excel(name = "手机号")
	@ApiModelProperty("手机号")
	private String mobile;
	/** 父亲手机 */
	@Excel(name = "父亲手机")
	@ApiModelProperty("父亲手机")
	private String fatherMobile;
	/** 母亲手机 */
	@Excel(name = "母亲手机")
	@ApiModelProperty("母亲手机")
	private String motherMobile;

}
