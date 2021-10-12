package com.ruoyi.demo.domain.bo;

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
 * 图片选择参数业务对象 hjc_selection
 *
 * @author qianlan
 * @date 2021-10-12
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("图片选择参数业务对象")
public class HjcSelectionBo extends BaseEntity {

    /**
     * hjc_pic表的主键
     */
	@ApiModelProperty("hjc_pic表的主键")
    private Long pid;

    /**
     * 选择
     */
	@ApiModelProperty("选择")
    private Integer selection;


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
