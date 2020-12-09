package com.ruoyi.system.fantang.entity;

import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportMealsDayEntity  extends FtReportMealsDao {
    // 用户自定义结算日期
    private Date selectBillingDate;
    // 自上一次结算累计未结算天数
    private Long days;
    // 上次缴费日期
    private Date lastCreateDate;
}
