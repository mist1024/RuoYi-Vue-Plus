package com.ruoyi.system.fantang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportMealsDayEntity {

    private Date createAt;

    private int days;
}
