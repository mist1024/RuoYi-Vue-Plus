package com.ruoyi.isc.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 应用服务业务对象 isc_app_service
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("应用服务业务对象")
public class IscAppServiceBo extends BaseEntity {

    /**
     * 应用服务ID
     */
    @ApiModelProperty(value = "应用服务ID")
    private Long serviceAppId;

    /**
     * 服务ID
     */
    @ApiModelProperty(value = "服务ID", required = true)
    @NotNull(message = "服务ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long serviceId;

    /**
     * 应用ID
     */
    @ApiModelProperty(value = "应用ID", required = true)
    @NotNull(message = "应用ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long applicationId;

    /**
     * 启用状态（0启用 1停用）
     */
    @ApiModelProperty(value = "启用状态（0启用 1停用）", required = true)
    @NotBlank(message = "启用状态（0启用 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String enabled;

    /**
     * 申请类型(0申请 1续期)
     */
    @ApiModelProperty(value = "申请类型(0申请 1续期)")
    private String applyType;

    /**
     * 审核状态（0待审核 1审核通过 2驳回）
     */
    @ApiModelProperty(value = "审核状态（0待审核 1审核通过 2驳回）")
    private String status;

    /**
     * 到期时间
     */
    @ApiModelProperty(value = "到期时间")
    private Date endTime;

    /**
     * 天配额
     */
    @ApiModelProperty(value = "天配额")
    private Long quotaDays;

    /**
     * 小时配额
     */
    @ApiModelProperty(value = "小时配额")
    private Long quotaHours;

    /**
     * 分钟配额
     */
    @ApiModelProperty(value = "分钟配额")
    private Long quotaMinutes;

    /**
     * 秒配额
     */
    @ApiModelProperty(value = "秒配额")
    private Long quotaSeconds;


    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private Integer pageNum;

    /**
     * 排序列
     */
    @ApiModelProperty("排序列")
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    @ApiModelProperty(value = "排序的方向", example = "asc,desc")
    private String isAsc;

}
