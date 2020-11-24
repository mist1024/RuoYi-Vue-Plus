package com.ruoyi.system.fantang.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 食品管理对象 ft_food
 * 
 * @author ft
 * @date 2020-11-24
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_food")
public class FtFoodDao implements Serializable {

private static final long serialVersionUID=1L;


    /** 食品id */
    @TableId(value = "food_id")
    private Long foodId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 图片 */
    private String pictureUrl;

    /** 售价 */
    @Excel(name = "售价")
    private BigDecimal price;

    /** 启用标志 */
    private Long flag;

    /** 食品分类 */
    @Excel(name = "食品分类")
    private Long type;
}
