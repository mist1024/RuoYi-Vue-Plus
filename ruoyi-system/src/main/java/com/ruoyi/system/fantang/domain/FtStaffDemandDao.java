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
 * 员工报餐对象 ft_staff_demand
 * 
 * @author ft
 * @date 2020-12-07
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_staff_demand")
public class FtStaffDemandDao implements Serializable {

private static final long serialVersionUID=1L;


    /** id */
    @TableId(value = "id")
    private Long id;

    /** 员工 id */
    @Excel(name = "员工 id")
    private Integer staffId;

    /** 正餐清单 */
    @Excel(name = "正餐清单")
    private String foods;

    /** 用餐类型 */
    @Excel(name = "用餐类型")
    private Long type;

    /** 创建时间 */
    @Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /** 创建人 */
    private Long createBy;

    /** 更新时间 */
    @Excel(name = "更新时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    /** 更新人 */
    private Long updateBy;

    /** 更新来源 */
    @Excel(name = "更新来源")
    private Integer updateFrom;

    /** 订单详情 */
    @Excel(name = "订单详情")
    private String orderInfo;

    /** 报餐模式 */
    @Excel(name = "报餐模式")
    private Boolean demandMode;

    /** 停用标志 */
    @Excel(name = "停用标志")
    private Integer stopFlag;
}
