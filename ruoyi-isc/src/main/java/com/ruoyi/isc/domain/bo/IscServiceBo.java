package com.ruoyi.isc.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务信息业务对象 isc_service
 *
 * @author ruoyi
 * @date 2021-08-22
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("服务信息业务对象")
public class IscServiceBo extends BaseEntity {

    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称", required = true)
    @NotBlank(message = "服务名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String serviceName;

    /**
     * 服务地址
     */
    @ApiModelProperty(value = "服务地址", required = true)
    @NotBlank(message = "服务地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String serviceAddr;

    /**
     * 探活地址
     */
    @ApiModelProperty(value = "探活地址", required = true)
    @NotBlank(message = "探活地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String probeActiveAddr;

    /**
     * 请求方式（默认GET）
     */
    @ApiModelProperty(value = "请求方式（默认GET）", required = true)
    @NotBlank(message = "请求方式（默认GET）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String requestMethod;

    /**
     * 跨域标志（Y是 N否）
     */
    @ApiModelProperty(value = "跨域标志（Y是 N否）", required = true)
    @NotBlank(message = "跨域标志（Y是 N否）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String corsFlag;

    /**
     * 隐藏参数
     */
    @ApiModelProperty(value = "隐藏参数")
    private String hiddenParams;

    /**
     * 是否在线（0离线 1在线）
     */
    @ApiModelProperty(value = "是否在线（0离线 1在线）")
    private String onlineStatus;

    /**
     * 审核状态（0待审核 1审核通过 2驳回）
     */
    @ApiModelProperty(value = "审核状态（0待审核 1审核通过 2驳回）")
    private String status;

    /**
     * JSON文档
     */
    @ApiModelProperty(value = "JSON文档", required = true)
    @NotBlank(message = "JSON文档不能为空", groups = { AddGroup.class, EditGroup.class })
    private String apiDoc;

    /**
     * 服务状态（0启用 1停用）
     */
    @ApiModelProperty(value = "服务状态（0启用 1停用）", required = true)
    @NotBlank(message = "服务状态（0启用 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String enabled;

    /**
     * 服务分类
     */
    @ApiModelProperty(value = "服务分类", required = true)
    @NotNull(message = "服务分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cateFullPath;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;


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
