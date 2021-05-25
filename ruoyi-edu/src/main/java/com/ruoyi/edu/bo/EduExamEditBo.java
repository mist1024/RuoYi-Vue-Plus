package com.ruoyi.edu.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import javax.validation.constraints.*;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 考试信息编辑对象 edu_exam
 *
 * @author keyleaf
 * @date 2021-05-23
 */
@Data
@ApiModel("考试信息编辑对象")
public class EduExamEditBo {


    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private Long id;

    /** 考试编号 */
    @ApiModelProperty("考试编号")
    @NotBlank(message = "考试编号不能为空")
    private String examNo;

    /** 考试名称 */
    @ApiModelProperty("考试名称")
    @NotBlank(message = "考试名称不能为空")
    private String examName;

    /** 考试简称 */
    @ApiModelProperty("考试简称")
    private String examShortName;

    /** 考试日期 */
    @ApiModelProperty("考试日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "考试日期不能为空")
    private Date examDate;
}
