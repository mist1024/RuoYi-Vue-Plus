package com.ruoyi.system.fantang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportMealsPriceEntity {

    /**
     * 正餐总价
     */
    private BigDecimal dinnerTotalPrice;

    /**
     * 营养餐总价
     */
    private BigDecimal nutritionTotalPrice;

    /**
     * 正餐和营养餐总价
     */
    private BigDecimal sumTotalPrice;
}
