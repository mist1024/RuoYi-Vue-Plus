package com.ruoyi.product.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 商品规格添加对象 pro_item_specification
 *
 * @author ruoyi
 * @date 2021-04-06
 */
@Data
@ApiModel("商品规格添加对象")
public class ProItemSpecificationAddBo {

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long itemId;
    /**
     * 规格名字
     */
    @ApiModelProperty("规格名字")
    private String specificationTitle;
    /**
     * 图片url地址
     */
    @ApiModelProperty("图片url地址")
    private String urlPath;
    /**
     * 规格库存
     */
    @ApiModelProperty("规格库存")
    private Integer specificationSku;
    /**
     * 价格
     */
    @ApiModelProperty("价格")
    private BigDecimal price;
    /**
     * 别名
     */
    @ApiModelProperty("别名")
    private String aliasName;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
    /**
     * 是否上架,0未上架,1上架,默认是0
     */
    @ApiModelProperty("是否上架,0未上架,1上架,默认是0")
    private String publishFlag;
    /**
     * 上架时间
     */
    @ApiModelProperty("上架时间")
    private LocalDateTime publishTime;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private String createBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
    /**
     * 更新人id
     */
    @ApiModelProperty("更新人id")
    private String updateBy;
}
