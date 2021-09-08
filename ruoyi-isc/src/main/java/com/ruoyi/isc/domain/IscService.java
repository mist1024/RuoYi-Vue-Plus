package com.ruoyi.isc.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 服务信息对象 isc_service
 *
 * @author Wenchao Gong
 * @date 2021-08-22
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("isc_service")
public class IscService implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * 服务ID
     */
    @TableId(value = "service_id")
    private Long serviceId;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务地址
     */
    private String serviceAddr;

    /**
     * 探活地址
     */
    private String probeActiveAddr;

    /**
     * 请求方式（默认GET）
     */
    private String requestMethod;

    /**
     * 备注
     */
    private String remark;

    /**
     * 跨域标志（Y是 N否）
     */
    private String corsFlag;

    /**
     * 隐藏参数
     */
    private String hiddenParams;

    /**
     * 是否在线（0离线 1在线）
     */
    private String onlineStatus;

    /**
     * 审核状态（0待审核 1审核通过 2驳回）
     */
    private String status;

    /**
     * JSON文档
     */
    private String apiDoc;

    /**
     * 审核意见
     */
    private String auditMind;

    /**
     * 服务状态（0启用 1停用）
     */
    private String enabled;

    /**
     * 服务分类全路径
     */
    private String cateFullPath;

    /**
     * 用户ID
     */
    private Long userId;

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

}
