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
 * 补贴管理对象 ft_subsidy
 *
 * @author ft
 * @date 2020-11-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_subsidy")
public class FtSubsidyDao implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 补贴 id
     */
    @TableId(value = "subsidy_id")
    private Long subsidyId;

    /**
     * 补贴类型
     */
    @Excel(name = "补贴类型")
    private String type;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal price;

    /**
     * 范围
     */
    @Excel(name = "范围")
    private String range;

    /**
     * 周期
     */
    @Excel(name = "周期")
    private String cycle;

    /**
     * 启用标志
     */
    private Integer flag;

    /**
     * 创建日期
     */
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createBy;
}
