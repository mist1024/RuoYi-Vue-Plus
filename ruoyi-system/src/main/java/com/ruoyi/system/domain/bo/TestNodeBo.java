package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 节点维护业务对象 test_node
 *
 * @author qianlan
 * @date 2021-08-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("节点维护业务对象")
public class TestNodeBo extends TreeEntity {

    /**
     * 主键
     */
	@ApiModelProperty("主键")
    private Long id;

    /**
     * 节点名
     */
	@ApiModelProperty("节点名")
    private String name;

    /**
     * 分类
     */
	@ApiModelProperty("分类")
    private String categary;

    /**
     * 父id
     */
	@ApiModelProperty("父id")
    private Long pid;


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
