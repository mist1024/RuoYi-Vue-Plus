package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ft_faceEvent")
public class FtFaceEventDao {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "device_id")
    private String deviceId;

    @TableField(value = "person_id")
    private String personId;

    @TableField(fill= FieldFill.INSERT)
    private int flag;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private int findFlag;
}
