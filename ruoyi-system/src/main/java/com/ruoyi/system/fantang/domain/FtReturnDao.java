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
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * 回款登记对象 ft_return
 * 
 * @author ft
 * @date 2021-01-25
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_return")
public class FtReturnDao implements Serializable {

private static final long serialVersionUID=1L;


    /** id */
    @TableId(value = "id")
    private Long id;

    /** 对应发票id */
    @Excel(name = "对应发票id")
    private Long invoiceId;

    /** 回款日期 */
    @Excel(name = "回款日期" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returnAt;

    /** 回款金额 */
    @Excel(name = "回款金额")
    private BigDecimal returnPrice;

    /** 余额 */
    @Excel(name = "余额")
    private BigDecimal balancePrice;

    /** 操作员 */
    @Excel(name = "操作员")
    private String opera;

    /** 凭证的图片url */
    @Excel(name = "凭证的图片url")
    private String voucherUrl;

    /** 是否完成回款标志 */
    @Excel(name = "是否完成回款标志")
    private Integer returnFlag;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
