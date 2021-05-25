package com.ruoyi.edu.vo;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 考试信息视图对象 mall_package
 *
 * @author keyleaf
 * @date 2021-05-23
 */
@Data
@ApiModel("考试信息视图对象")
public class EduExamVo {
	private static final long serialVersionUID = 1L;

	/** $pkColumn.columnComment */
	@ApiModelProperty("$pkColumn.columnComment")
	private Long id;

	/** 考试编号 */
	@Excel(name = "考试编号")
	@ApiModelProperty("考试编号")
	private String examNo;
	/** 考试名称 */
	@Excel(name = "考试名称")
	@ApiModelProperty("考试名称")
	private String examName;
	/** 考试简称 */
	@Excel(name = "考试简称")
	@ApiModelProperty("考试简称")
	private String examShortName;
	/** 考试日期 */
	@Excel(name = "考试日期" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("考试日期")
	private Date examDate;

}
