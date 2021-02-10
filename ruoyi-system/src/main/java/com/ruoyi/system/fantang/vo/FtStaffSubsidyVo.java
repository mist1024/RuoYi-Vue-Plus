package com.ruoyi.system.fantang.vo;

import com.ruoyi.system.fantang.domain.FtStaffInfoDao;
import com.ruoyi.system.fantang.domain.FtSubsidyDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FtStaffSubsidyVo {

    private Date giveOutDate;

    private List<FtStaffInfoDao> staffData;

    private FtSubsidyDao subsidy;

    private List<String> noGiveoutList;

}
