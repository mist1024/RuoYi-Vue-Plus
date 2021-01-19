package com.ruoyi.system.fantang.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.fantang.domain.FtPatientDao;
import com.ruoyi.system.fantang.domain.FtReportMealsDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FtPatientVo extends FtPatientDao {

    @TableField(exist = false)
    private Boolean openFlag;
    List<FtReportMealsDao> reportMealsList;

}
