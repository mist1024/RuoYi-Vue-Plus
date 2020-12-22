package com.ruoyi.system.fantang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettleEntity {

    // 床号
    private String bedId;

    // 科室名
    private String departName;

    // 住院号
    private String hospitalId;

    // 上次缴费日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastBillingDate;

    // 病人姓名
    private String name;

    // 实收
    private BigDecimal netPeceipt;

    // 操作员
    private String opera;

    // 病人 id
    private Long patientId;

    // 预付款金额
    private BigDecimal prepayment;

    // 应收
    private BigDecimal price;

    // 用户自定义结算日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date selectBillingDate;

    // 自上一次结算累计未结算天数
    private Long days;

    // 操作员
    private String userName;

}
