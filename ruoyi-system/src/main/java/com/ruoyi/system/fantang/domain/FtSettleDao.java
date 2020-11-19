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
 * 结算报对象 ft_settle
 *
 * @author ft
 * @date 2020-11-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_settle")
public class FtSettleDao implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 结算 id
     */
    @TableId(value = "settle_id")
    private Long settleId;

    /**
     * 病人 id
     */
    private Long patientId;

    /**
     * 结算日期
     */
    @Excel(name = "结算日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleAt;

    /**
     * 操作员
     */
    private String opera;

    /**
     * 记录清单
     */
    @Excel(name = "记录清单")
    private String list;

    /**
     * 结算总价
     */
    @Excel(name = "结算总价")
    private BigDecimal price;

    /**
     * 应收
     */
    @Excel(name = "应收")
    private BigDecimal payable;

    /**
     * 实收
     */
    @Excel(name = "实收")
    private BigDecimal receipts;

    /**
     * 结算类型
     */
    @Excel(name = "结算类型")
    private String type;

    /**
     * 退押金标志
     */
    private Integer flag;

    /**
     * 退款总额
     */
    @Excel(name = "退款总额")
    private BigDecimal refund;
}
