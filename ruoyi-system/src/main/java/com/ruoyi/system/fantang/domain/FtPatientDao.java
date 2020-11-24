package com.ruoyi.system.fantang.domain;

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
 * 病人管理对象 ft_patient
 * 
 * @author ft
 * @date 2020-11-24
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_patient")
public class FtPatientDao implements Serializable {

private static final long serialVersionUID=1L;


    /** 病人id */
    @TableId(value = "patient_id")
    private Long patientId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 所属部门id */
    private Long departId;

    /** 床号 */
    @Excel(name = "床号")
    private String bedId;

    /** 住院号 */
    @Excel(name = "住院号")
    private String hospitalId;

    /** 同步标志 */
    private Integer syncFlag;

    /** 出院标志 */
    private Integer offFlag;

    /** 创建时间 */
    private Date createAt;
}
