package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 报餐管理对象 ft_report_meals
 *
 * @author ft
 * @date 2020-11-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_report_meals")
public class FtReportMealsDao implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * $column.columnComment
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 报餐日期
     */
    @Excel(name = "报餐日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 报餐类型
     */
    @Excel(name = "报餐类型")
    private Long type;

    /**
     * 病人id
     */
    private Long patientId;

    /**
     * 报餐人
     */
    @Excel(name = "报餐人")
    private Long createBy;

    /**
     * 订单列表
     */
    @Excel(name = "订单列表")
    private String foods;

    /**
     * 正餐总价
     */
    @Excel(name = "总价")
    private BigDecimal price;

    /**
     * 结算标志
     */
    private Integer settlementFlag;

    private Long settlementId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settlementAt;

    private String settlementBy;

    @TableField(exist = false)
    private String hospitalId;

    @TableField(exist = false)
    private String departId;

    @TableField(exist = false)
    private String departName;

    @TableField(exist = false)
    private String bedId;

    @TableField(exist = false)
    private String name;

    /**
     * 营养餐 id
     */
    private Long nutritionFoodId;

    /**
     * 营养配餐标志
     */
    private Integer nutritionFoodFlag;

    /**
     * 是否替代正餐
     */
    private Boolean isReplaceFood;

    /**
     * 营养配餐价格
     */
    private BigDecimal nutritionFoodPrice;

    /**
     * 当前报餐总价
     */
    private BigDecimal totalPrice;
    
    private Boolean vegetables;
    
    private Boolean meat;
    
    private Boolean rice;
    
    private Integer egg;
}
