package com.ruoyi.edu.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生信息分页查询对象 edu_student
 *
 * @author keyleaf
 * @date 2021-05-22
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("学生信息分页查询对象")
public class EduStudentQueryBo extends BaseEntity {

	/** 分页大小 */
	@ApiModelProperty("分页大小")
	private Integer pageSize;
	/** 当前页数 */
	@ApiModelProperty("当前页数")
	private Integer pageNum;
	/** 排序列 */
	@ApiModelProperty("排序列")
	private String orderByColumn;
	/** 排序的方向desc或者asc */
	@ApiModelProperty(value = "排序的方向", example = "asc,desc")
	private String isAsc;


	/** 身份证号 */
	@ApiModelProperty("身份证号")
	private String idCardNo;
	/** 学生姓名 */
	@ApiModelProperty("学生姓名")
	private String studentName;
	/** 年级 */
	@ApiModelProperty("年级")
	private String currentGrade;
	/** 班级 */
	@ApiModelProperty("班级")
	private String currentClass;

}
