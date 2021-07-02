package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品规格对象 pro_item_specification
 *
 * @author ruoyi
 * @date 2021-04-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pro_item_specification")
public class ProItemSpecification implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long itemId;

    /**
     * 规格名字
     */
    private String specificationTitle;

    /**
     * 图片url地址
     */
    private String urlPath;

    /**
     * 规格库存
     */
    private Integer specificationSku;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 别名
     */
    private String aliasName;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否上架,0未上架,1上架,默认是0
     */
    private String publishFlag;

    /**
     * 上架时间
     */
    private LocalDateTime publishTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更新人id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
