package com.ruoyi.system.fantang.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FtCateringVo {

    @TableField(exist = false)
    private String bedId;

    @TableField(exist = false)
    private Long departId;

    @TableField(exist = false)
    private List<Integer> types;

    @TableField(exist = false)
    private String departName;

    @TableField(exist = false)
    private String name;

}
