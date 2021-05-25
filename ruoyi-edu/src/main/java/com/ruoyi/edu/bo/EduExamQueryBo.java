package com.ruoyi.edu.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考试信息分页查询对象 edu_exam
 *
 * @author keyleaf
 * @date 2021-05-23
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("考试信息分页查询对象")
public class EduExamQueryBo extends BaseEntity {

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


	/** 考试编号 */
	@ApiModelProperty("考试编号")
	private String examNo;
	/** 考试名称 */
	@ApiModelProperty("考试名称")
	private String examName;
	/** 考试简称 */
	@ApiModelProperty("考试简称")
	private String examShortName;
	/** 考试日期 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("考试日期")
	private Date examDate;

}
