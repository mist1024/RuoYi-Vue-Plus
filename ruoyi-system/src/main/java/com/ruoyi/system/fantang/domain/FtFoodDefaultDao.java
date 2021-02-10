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
 * 默认报餐管理对象 ft_food_default
 * 
 * @author ft
 * @date 2020-11-25
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_food_default")
public class FtFoodDefaultDao implements Serializable {

private static final long serialVersionUID=1L;


    /** id */
    @TableId(value = "id")
    private Long id;

    /** 报餐类型 */
    @Excel(name = "报餐类型")
    private Long type;

    /** 菜品列表 */
    private String foodList;

    /** 创建日期 */
    @Excel(name = "创建日期" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /** 创建人 */
    private Long createdBy;

    /** 更新日期 */
    @Excel(name = "更新日期" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    /** 更新人 */
    private Long updatedBy;

    /** 总价格 */
    @Excel(name = "总价格")
    private BigDecimal price;
}
