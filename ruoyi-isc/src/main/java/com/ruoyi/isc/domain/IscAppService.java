package com.ruoyi.isc.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用服务对象 isc_app_service
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("isc_app_service")
public class IscAppService implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * 应用服务ID
     */
    @TableId(value = "app_service_id")
    private Long appServiceId;

    /**
     * 服务ID
     */
    private Long serviceId;

    /**
     * 应用ID
     */
    private Long applicationId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 启用状态（0启用 1停用）
     */
    private String enabled;

    /**
     * 申请类型(0申请 1续期)
     */
    private String applyType;

    /**
     * 审核状态（0待审核 1审核通过 2驳回）
     */
    private String status;

    /**
     * 虚拟地址
     */
    private String virtualAddr;

    /**
     * 到期时间
     */
    private Date endTime;

    /**
     * 天配额
     */
    private Long quotaDays;

    /**
     * 小时配额
     */
    private Long quotaHours;

    /**
     * 分钟配额
     */
    private Long quotaMinutes;

    /**
     * 秒配额
     */
    private Long quotaSeconds;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 备注
     */
    private String remark;

}
