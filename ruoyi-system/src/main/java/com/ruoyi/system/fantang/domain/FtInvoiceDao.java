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
 * 财务收费开票对象 ft_invoice
 * 
 * @author ft
 * @date 2020-12-08
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_invoice")
public class FtInvoiceDao implements Serializable {

private static final long serialVersionUID=1L;


    /** id */
    @TableId(value = "id")
    private Long id;

    /** 发票单位 */
    private String invoiceUnit;

    /** 发票 id */
    private Long invoiceId;

    /** 日期 */
    private Date createAt;

    /** 开票人 */
    private String drawer;

    /** 收款方式 */
    private Integer collectionType;

    /** 应收 */
    private BigDecimal payable;

    /** 实收 */
    private BigDecimal receipts;

    /** 凭证列表 */
    private String voucherList;
}
