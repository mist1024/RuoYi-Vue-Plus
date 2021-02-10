package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 订单管理对象 ft_order
 *
 * @author ft
 * @date 2020-11-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_order")
public class FtOrderDao implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 订单 id
     */
    @TableId(value = "order_id")
    private Long orderId;

    @TableField(exist = false)
    @Excel(name = "部门名称")
    private String departName;

    /**
     * 员工姓名
     */
    @TableField(exist = false)
    @Excel(name = "员工姓名")
    private String name;

    @TableField(exist = false)
    @Excel(name = "电话")
    private String tel;

    /**
     * 订单类型
     */
    @Excel(name = "订单类型", readConverterExp = "1=早餐,2=午餐,3=晚餐")
    private Integer orderType;

    @TableField(exist = false)
    private String orderTypeString;

    /**
     * 员工 id
     */
    private Long staffId;

    /**
     * 清单
     */
    private String orderList;

    /**
     * 总价
     */
    @Excel(name = "价格")
    private BigDecimal totalPrice;

    /**
     * 统计总数
     */
    @TableField(exist = false)
    private Long total;

    /**
     * 报餐类型
     */
    @TableField(exist = false)
    private Integer type;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 实收
     */
    private BigDecimal receipts;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    // 订用餐日期
    @Excel(name = "订用餐日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 订单来源
     */
    private String orderSrc;

    /**
     * 订单现售
     */
    private BigDecimal currentPrice;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 支付标志
     */
    private Integer payFlag;

    /**
     * 过期标志
     */
    private Integer expiredFlag;

    /**
     * 核销标志
     */
    @Excel(name = "是否核销", readConverterExp = "0=否,1=是")
    private Integer writeOffFlag;

    /**
     * 核销时间
     */
    @Excel(name = "核销时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date writeOffAt;

    /**
     * 是否过期
     */
    private Integer isExpired;

    /**
     * 核销设备 id
     */
    private Long deviceId;

    @TableField(exist = false)
    private Integer countOrder;

    @TableField(exist = false)
    private Long departId;

    @TableField(exist = false)
    private String staffName;

}
