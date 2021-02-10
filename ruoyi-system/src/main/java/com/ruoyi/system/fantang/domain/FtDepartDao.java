package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 科室管理对象 ft_depart
 * 
 * @author ft
 * @date 2020-11-24
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_depart")
public class FtDepartDao implements Serializable {

private static final long serialVersionUID=1L;


    /** 科室编号 */
    @TableId(value = "depart_id")
    private Long departId;

    /** 科室名称 */
    @Excel(name = "科室名称")
    private String departName;

    /** 科室编号 */
    @Excel(name = "科室编号")
    private String departCode;

    @TableField(exist = false)
    private Long patientId;
    @TableField(exist = false)
    private String bedId;
    @TableField(exist = false)
    private String name;
}
