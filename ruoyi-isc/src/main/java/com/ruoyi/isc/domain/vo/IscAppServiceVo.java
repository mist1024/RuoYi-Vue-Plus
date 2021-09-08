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
 * 应用服务视图对象 isc_app_service
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */
@Data
@ApiModel("应用服务视图对象")
@ExcelIgnoreUnannotated
public class IscAppServiceVo {

    private static final long serialVersionUID = 1L;

    /**
     *  应用服务ID
     */
    @ApiModelProperty("应用服务ID")
    private Long serviceAppId;

    /**
     * 服务ID
     */
    @ApiModelProperty("服务ID")
    private Long serviceId;

    /**
     * 服务名称
     */
    @ExcelProperty(value = "服务名称")
    @ApiModelProperty("服务名称")
    private String serviceName;

    /**
     * 应用ID
     */
    @ExcelProperty(value = "应用ID")
    @ApiModelProperty("应用ID")
    private Long applicationId;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 启用状态（0启用 1停用）
     */
    @ExcelProperty(value = "启用状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    @ApiModelProperty("启用状态（0启用 1停用）")
    private String enabled;

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
     * 虚拟地址
     */
    @ExcelProperty(value = "虚拟地址")
    @ApiModelProperty("虚拟地址")
    private String virtualAddr;

    /**
     * 到期时间
     */
    @ExcelProperty(value = "到期时间")
    @ApiModelProperty("到期时间")
    private Date endTime;

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
