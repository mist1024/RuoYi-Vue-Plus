package com.ruoyi.isc.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 应用信息业务对象 isc_application
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("应用信息业务对象")
public class IscApplicationBo extends BaseEntity {

    /**
     * 应用ID
     */
    @ApiModelProperty(value = "应用ID")
    private Long applicationId;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称", required = true)
    @NotBlank(message = "应用名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applicationName;

    /**
     * 应用密钥
     */
    @ApiModelProperty(value = "应用密钥", required = true)
    private String accessKey;

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
