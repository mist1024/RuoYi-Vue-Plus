package com.ruoyi.system.fantang.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FtStaffInfoVo {
    @TableField(exist = false)
    private String departName;
    @TableField(exist = false)
    private Boolean giveOutFlag;
}
