package com.ruoyi.isc.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 应用服务申请信息业务对象 isc_app_service_apply
 *
 * @author Wenchao Gong
 * @date 2021-09-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("应用服务申请信息业务对象")
public class IscAppServiceApplyBo extends BaseEntity {

    /**
     * 申请ID
     */
    @ApiModelProperty(value = "申请ID")
    private Long applyId;

    /**
     * 应用服务ID
     */
    @ApiModelProperty(value = "应用服务ID", required = true)
    @NotNull(message = "应用服务ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long appServiceId;

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
     * 续期时长（单位月）
     */
    @ApiModelProperty(value = "续期时长（单位月）")
    private Integer renewalDuration;

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

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
