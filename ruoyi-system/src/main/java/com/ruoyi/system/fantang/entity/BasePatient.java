package com.ruoyi.system.fantang.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePatient {
    @TableField(exist = false)
    public String hospitalId;
    @TableField(exist = false)
    public String bedId;
    @TableField(exist = false)
    public String name;
    @TableField(exist = false)
    public String departName;
    @TableField(exist = false)
    private Long departId;
    @TableField(exist = false)
    private Long total;
}
