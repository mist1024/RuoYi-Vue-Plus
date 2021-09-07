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
 * 服务信息视图对象 isc_service
 *
 * @author ruoyi
 * @date 2021-08-22
 */
@Data
@ApiModel("服务信息视图对象")
@ExcelIgnoreUnannotated
public class IscServiceVo {

    private static final long serialVersionUID = 1L;

    /**
     *  服务ID
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
     * 服务地址
     */
    @ExcelProperty(value = "服务地址")
    @ApiModelProperty("服务地址")
    private String serviceAddr;

    /**
     * 探活地址
     */
    @ExcelProperty(value = "探活地址")
    @ApiModelProperty("探活地址")
    private String probeActiveAddr;

    /**
     * 请求方式（默认GET）
     */
    @ExcelProperty(value = "请求方式")
    @ApiModelProperty("请求方式（默认GET）")
    private String requestMethod;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 跨域标志（Y是 N否）
     */
    @ExcelProperty(value = "跨域标志")
    @ApiModelProperty("跨域标志（Y是 N否）")
    private String corsFlag;

    /**
     * 隐藏参数
     */
    @ExcelProperty(value = "隐藏参数")
    @ApiModelProperty("隐藏参数")
    private String hiddenParams;

    /**
     * JSON文档
     */
    @ApiModelProperty("JSON文档")
    private String apiDoc;

    /**
     * 是否在线（0离线 1在线）
     */
    @ExcelProperty(value = "是否在线", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "isc_online_status")
    @ApiModelProperty("是否在线（0离线 1在线）")
    private String onlineStatus;

    /**
     * 审核状态（0待审核 1审核通过 2驳回）
     */
    @ExcelProperty(value = "审核状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_audit_status")
    @ApiModelProperty("审核状态（0待审核 1审核通过 2驳回）")
    private String status;

    /**
     * 服务状态（0启用 1停用）
     */
    @ExcelProperty(value = "服务状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    @ApiModelProperty("服务状态（0启用 1停用）")
    private String enabled;

    /**
     * 服务分类
     */
    @ApiModelProperty("服务分类")
    private String cateFullPath;

    /**
     * 服务分类名称
     */
    @ExcelProperty(value = "服务分类名称")
    @ApiModelProperty("服务分类名称")
    private String cateName;

    /**
     * 更新人
     */
    @ExcelProperty(value = "更新人")
    @ApiModelProperty("更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @ApiModelProperty("更新时间")
    private String updateTime;


}
