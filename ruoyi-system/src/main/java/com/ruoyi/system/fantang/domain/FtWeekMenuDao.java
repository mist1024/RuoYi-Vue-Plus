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
 * 每周菜单对象 ft_week_menu
 * 
 * @author ft
 * @date 2020-11-27
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_week_menu")
public class FtWeekMenuDao implements Serializable {

private static final long serialVersionUID=1L;


    /** id */
    @TableId(value = "id")
    private Long id;

    /** 用餐类型 */
    @Excel(name = "用餐类型")
    private Integer dinnerType;

    /** 星期几 */
    @Excel(name = "星期几")
    private Integer weekday;

    /** 菜品列表 */
    @Excel(name = "菜品列表")
    private String foods;

    /** 总价格 */
    @Excel(name = "总价格")
    private BigDecimal price;

    /** 启用标志 */
    @Excel(name = "启用标志")
    private Integer flag;
}
