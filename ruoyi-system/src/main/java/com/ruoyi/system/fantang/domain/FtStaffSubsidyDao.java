package com.ruoyi.system.fantang.domain;

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
 * 补贴流水查看对象 ft_staff_subsidy
 *
 * @author ft
 * @date 2020-11-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_staff_subsidy")
public class FtStaffSubsidyDao implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 补贴流水 id
     */
    @TableId(value = "subsidy_id")
    private Long subsidyId;

    /**
     * 员工 id
     */
    private Long staffId;

    /**
     * 补贴类型
     */
    @Excel(name = "补贴类型")
    private String subsidyType;

    /**
     * 收支类型
     */
    @Excel(name = "收支类型")
    private Integer incomeType;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal price;

    /**
     * 消费日期
     */
    @Excel(name = "消费日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date consumAt;

    /**
     * 消费订单 id
     */
    private Long orderId;
}
