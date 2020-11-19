package com.ruoyi.system.fantang.domain;

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
 * 病人管理对象 ft_patient
 *
 * @author ft
 * @date 2020-11-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_patient")
public class FtPatientDao implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 病人id
     */
    @TableId(value = "patient_id")
    private Long patientId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 所属部门id
     */
    private Long departId;

    /**
     * 床号
     */
    @Excel(name = "床号")
    private String bedId;
}
