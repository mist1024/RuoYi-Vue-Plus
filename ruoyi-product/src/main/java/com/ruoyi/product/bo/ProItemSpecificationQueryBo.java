package com.ruoyi.product.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


/**
 * 商品规格分页查询对象 pro_item_specification
 *
 * @author ruoyi
 * @date 2021-04-06
 */
@Data
@ApiModel("商品规格分页查询对象")
public class ProItemSpecificationQueryBo {

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
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long itemId;

    @ApiModelProperty("规格标题")
    private String specificationTitle;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
