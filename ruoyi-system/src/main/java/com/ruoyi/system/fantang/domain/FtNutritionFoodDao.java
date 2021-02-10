package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 病患营养配餐对象 ft_nutrition_food
 *
 * @author ft
 * @date 2020-12-03
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_nutrition_food")
public class FtNutritionFoodDao implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 营养餐名称
     */
    @Excel(name = "营养餐名称")
    private String name;

    /**
     * 价格
     */
    @Excel(name = "价格")
    private BigDecimal price;

    /**
     * 启用标志
     */
    @Excel(name = "启用标志")
    private Boolean flag;

    /**
     * 创建日期
     */
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy;
}
