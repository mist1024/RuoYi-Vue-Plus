package com.ruoyi.isc.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 应用服务申请信息视图对象 isc_app_service_apply
 *
 * @author Wenchao Gong
 * @date 2021-09-09
 */
@Data
@ApiModel("应用服务申请信息视图对象")
@ExcelIgnoreUnannotated
public class IscAppServiceApplyVo {

    private static final long serialVersionUID = 1L;

    /**
     *  申请ID
     */
    @ApiModelProperty("申请ID")
    private Long applyId;

    /**
     * 应用服务ID
     */
    @ApiModelProperty("应用服务ID")
    private Long appServiceId;

    /**
     * 应用名称
     */
    @ExcelProperty(value = "应用名称")
    @ApiModelProperty("应用名称")
    private String applicationName;

    /**
     * 服务名称
     */
    @ExcelProperty(value = "服务名称")
    @ApiModelProperty("服务名称")
    private String serviceName;

    /**
     * 申请类型(0申请 1续期)
     */
    @ExcelProperty(value = "申请类型(0申请 1续期)", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "isc_apply_type")
    @ApiModelProperty("申请类型(0申请 1续期)")
    private String applyType;

    /**
     * 审核状态（0待审核 1审核通过 2驳回）
     */
    @ExcelProperty(value = "审核状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_audit_status")
    @ApiModelProperty("审核状态（0待审核 1审核通过 2驳回）")
    private String status;

    /**
     * 续期时长（单位月）
     */
    @ExcelProperty(value = "续期时长", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "单=位月")
    @ApiModelProperty("续期时长（单位月）")
    private Long renewalDuration;

    /**
     * 天配额
     */
    @ExcelProperty(value = "天配额")
    @ApiModelProperty("天配额")
    private Long quotaDays;

    /**
     * 小时配额
     */
    @ExcelProperty(value = "小时配额")
    @ApiModelProperty("小时配额")
    private Long quotaHours;

    /**
     * 分钟配额
     */
    @ExcelProperty(value = "分钟配额")
    @ApiModelProperty("分钟配额")
    private Long quotaMinutes;

    /**
     * 秒配额
     */
    @ExcelProperty(value = "秒配额")
    @ApiModelProperty("秒配额")
    private Long quotaSeconds;

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

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    private String remark;


}
