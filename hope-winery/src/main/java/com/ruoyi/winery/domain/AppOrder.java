package com.ruoyi.winery.domain;

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
import java.util.List;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对象 app_order
 * 
 * @author ruoyi
 * @date 2021-01-18
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_order")
public class AppOrder implements Serializable {

private static final long serialVersionUID=1L;


    /** 订单ID */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 收货地址ID */
    @Excel(name = "收货地址ID")
    private String addressId;

    /** 支付参数 */
    @Excel(name = "支付参数")
    private String payMsg;

    /** 总金额 */
    @Excel(name = "总金额")
    private Integer totalFee;

    /** 运单号 */
    @Excel(name = "运单号")
    private String transportNo;

    /** 流水号 */
    @Excel(name = "流水号")
    private String transitionId;

    /** 订单状态（0待支付1已取消2已支付3待收货4交易完成） */
    @Excel(name = "订单状态" , readConverterExp = "0=待支付,1=已取消,2=已支付,3=待收货,4=交易完成")
    private Integer status;

    /** 支付时间 */
    @Excel(name = "支付时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 取消时间 */
    @Excel(name = "取消时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    private List<AppOrderDetail> orderDetailList;
}
