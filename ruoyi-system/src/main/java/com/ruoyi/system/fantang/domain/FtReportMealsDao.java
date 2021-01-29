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
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 科室名
     */
    @TableField(exist = false)
    @Excel(name = "部门名称")
    private String departName;

    /**
     * 住院号
     */
    @TableField(exist = false)
    @Excel(name = "住院号")
    private String hospitalId;

    /**
     * 床号
     */
    @TableField(exist = false)
    @Excel(name = "床号")
    private String bedId;

    /**
     * 病人姓名
     */
    @TableField(exist = false)
    @Excel(name = "姓名")
    private String name;

    /**
     * 报餐日期
     */
    @Excel(name = "报餐日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 报餐类型
     */
    @Excel(name = "报餐类型", readConverterExp = "1=早餐,2=午餐,3=晚餐,4=加餐")
    private Long type;

    /**
     * 病人id
     */
    private Long patientId;

    /**
     * 报餐人
     */
    private Long createBy;

    /**
     * 订单列表
     */
    private String foods;

    /**
     * 正餐总价
     */
    @Excel(name = "正餐总价")
    private BigDecimal price;

    /**
     * 结算标志
     */
    private Integer settlementFlag;

    private Long settlementId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settlementAt;

    private String settlementBy;

    /**
     * 科室 id
     */
    @TableField(exist = false)
    private String departId;

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
    @Excel(name = "营养配餐价格")
    private BigDecimal nutritionFoodPrice;

    /**
     * 当前报餐总价
     */
    @Excel(name = "当餐总价")
    private BigDecimal totalPrice;

    private Boolean vegetables;

    private Boolean meat;

    private Boolean rice;

    private Integer egg;

    @TableField(exist = false)
    private Integer total;

    private Boolean openFlag;

    @TableField(exist = false)
    private Integer statisticsType;

    /**
     * 营养餐名称
     */
    @TableField(exist = false)
    private String nutritionName;

    @TableField(exist = false)
    private Integer cateringUsage;

    /**
     * 用餐开始时间
     */
    @TableField(exist = false)
    private Date beginOfDay;

    /**
     * 用餐结束时间
     */
    @TableField(exist = false)
    private Date endOfDay;

    /**
     * 用餐标志
     */
    @Excel(name = "是否已用餐", readConverterExp = "1=是,0=否")
    private Integer diningFlag;

    @Excel(name = "用餐日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date diningAt;
}
