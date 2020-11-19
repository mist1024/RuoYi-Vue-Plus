package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 特殊用餐管理对象 ft_special_demand
 *
 * @author ft
 * @date 2020-11-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_special_demand")
public class FtSpecialDemandDao implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 病人id
     */
    private Long patientId;

    /**
     * 订单详情
     */
    @Excel(name = "订单详情")
    private String foods;

    /**
     * 用餐类型
     */
    @Excel(name = "用餐类型")
    private Long type;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 总价
     */
    @Excel(name = "总价")
    private BigDecimal price;

    /**
     * 有效期
     */
    @Excel(name = "有效期")
    private Long term;

    /**
     * 更新日期
     */
    private Date updateAt;

    /**
     * 更新操作人 id
     */
    private Long updateBy;
}
