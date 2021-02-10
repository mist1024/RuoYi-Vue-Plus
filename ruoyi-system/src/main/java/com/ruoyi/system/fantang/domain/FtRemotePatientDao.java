package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 远程病患数据实体类
 * 
 * @author 陈智兴
 * @date 2020-11-24
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_sync")
public class FtRemotePatientDao implements Serializable {

private static final long serialVersionUID=1L;


    /** 住院号 */
    @TableField(value = "hospital_id")
    private String hospitalid;

    /** 姓名 */
    private String name;

    // 科室名称
    @TableField("depart_name")
    private String departName;

    // 床号
    @TableField("bed_id")
    private String bedId;

}
