package com.ruoyi.system.fantang.vo;

import com.ruoyi.system.fantang.domain.FtDepartDao;
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
public class FtDepartVo extends FtDepartDao {

    List<FtReportMealsDao> reportMealsList;
}
