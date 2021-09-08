package com.ruoyi.isc.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;



/**
 * 应用信息视图对象 isc_application
 *
 * @author wenchao gong
 * @date 2021-09-08
 */
@Data
@ApiModel("应用信息视图对象")
@ExcelIgnoreUnannotated
public class IscApplicationVo {

    private static final long serialVersionUID = 1L;

    /**
     *  应用ID
     */
    @ApiModelProperty("应用ID")
    private Long applicationId;

    /**
     * 应用名称
     */
    @ExcelProperty(value = "应用名称")
    @ApiModelProperty("应用名称")
    private String applicationName;

    /**
     * 应用密钥
     */
    @ExcelProperty(value = "应用密钥")
    @ApiModelProperty("应用密钥")
    private String accessKey;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 更新者
     */
    @ExcelProperty(value = "更新者")
    @ApiModelProperty("更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @ApiModelProperty("更新时间")
    private Date updateTime;


}
