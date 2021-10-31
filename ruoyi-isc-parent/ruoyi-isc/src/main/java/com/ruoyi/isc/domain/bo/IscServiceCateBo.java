package com.ruoyi.isc.domain.bo;

import com.ruoyi.common.core.domain.TreeEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 服务分类业务对象 isc_service_cate
 *
 * @author Wenchao Gong
 * @date 2021-08-22
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("服务分类业务对象")
public class IscServiceCateBo extends TreeEntity {

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    private Long cateId;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String cateName;

    /**
     * 启用状态（0启用 1停用）
     */
    @ApiModelProperty(value = "启用状态（0启用 1停用）", required = true)
    @NotBlank(message = "启用状态（0启用 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String enabled;


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
