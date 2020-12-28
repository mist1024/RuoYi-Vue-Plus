package com.ruoyi.winery.domain;

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
 * 客户订单对象 winery_orders
 *
 * @author ruoyi
 * @date 2020-12-28
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("winery_orders")
public class WineryOrders implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 订单ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 商品ID
     */
    @Excel(name = "商品ID")
    private Long goodsId;

    /**
     * 商品简称
     */
    @Excel(name = "商品简称")
    private String goodsName;

    /**
     * 商品类型
     */
    @Excel(name = "商品类型")
    private String goodsType;

    /**
     * 商品规格
     */
    @Excel(name = "商品规格")
    private String goodsSpec;

    /**
     * 商品封面
     */
    @Excel(name = "商品封面")
    private String goodsFaceImg;

    /**
     * 商品基准单价
     */
    @Excel(name = "商品基准单价")
    private Long goodsPrice;

    /**
     * 商品数量
     */
    @Excel(name = "商品数量")
    private Integer goodsCount;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 订单状态:
     * 0.未支付
     * 1.已取消
     * 2.支付成功，等待发货
     * 3.支付成功，已发货
     * 4.已收货
     * 5.退款申请
     * 6.退款中
     * 7.退款成功
     */
    @Excel(name = "订单状态: 0.未支付 1.已取消 2.支付成功，等待发货 3.支付成功，已发货 4.已收货 5.退款申请 6.退款中 7.退款成功")
    private Integer orderStatus;
}
