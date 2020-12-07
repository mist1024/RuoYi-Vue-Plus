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
 * 配餐功能对象 ft_catering
 * 
 * @author ft
 * @date 2020-12-07
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_catering")
public class FtCateringDao implements Serializable {

private static final long serialVersionUID=1L;


    /** id */
    @TableId(value = "id")
    private Long id;

    /** 病人 id */
    private Long patientId;

    /** 正餐类型 */
    @Excel(name = "正餐类型")
    private Integer type;

    /** 配餐号 */
    @Excel(name = "配餐号")
    private Long number;

    /** 配餐频次 */
    @Excel(name = "配餐频次")
    private String frequency;

    /** 用法 */
    @Excel(name = "用法")
    private Integer usage;

    /** 是否代替正餐 */
    private Integer isReplace;

    /** 作废标志 */
    private Integer flag;

    /** 更新日期 */
    private Date updateAt;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 创建时间 */
    @Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /** 创建人 */
    private String createBy;

    /** 描述 */
    @Excel(name = "描述")
    private String describe;
}
