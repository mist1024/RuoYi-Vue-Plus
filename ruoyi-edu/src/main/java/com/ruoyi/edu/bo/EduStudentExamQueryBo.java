package com.ruoyi.edu.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生考试信息分页查询对象 edu_student_exam
 *
 * @author keyleaf
 * @date 2021-05-23
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("学生考试信息分页查询对象")
public class EduStudentExamQueryBo extends BaseEntity {

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


	/** 学生id */
	@ApiModelProperty("学生id")
	private Long studentId;
	/** 考试id */
	@ApiModelProperty("考试id")
	private Long examId;

}
