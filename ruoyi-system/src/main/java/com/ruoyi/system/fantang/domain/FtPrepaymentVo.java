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
 * 收费管理对象 ft_prepayment
 *
 * @author ft
 * @date 2020-11-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_prepayment")
public class FtPrepaymentVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 预付费id
     */
    @TableId(value = "prepayment_id")
    private Long prepaymentId;

    /**
     * 病人id
     */
    private Long patientId;

    /**
     * 收款时间
     */
    @Excel(name = "收款时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date collectAt;

    /**
     * 收款员
     */
    private Long collectBy;

    /**
     * 结算时间
     */
    @Excel(name = "结算时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settlementAt;

    /**
     * 结算员
     */
    private Long settlementBy;

    /**
     * 结算报表id
     */
    private Long settlementId;

    /**
     * 结算标志
     */
    @Excel(name = "结算标志")
    private Integer settlementFlag;

    /**
     * 预付费金额
     */
    @Excel(name = "预付费金额")
    private BigDecimal prepaid;

    /**
     * 预付费时间
     */
    @Excel(name = "预付费时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date prepaidAt;
}
