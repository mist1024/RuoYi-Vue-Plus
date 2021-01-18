package com.ruoyi.winery.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.winery.domain.goods.GoodsMain;
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
 * 订单明细对象 app_order_detail
 * 
 * @author ruoyi
 * @date 2021-01-18
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_order_detail")
public class AppOrderDetail implements Serializable {

private static final long serialVersionUID=1L;


    /** 明细ID */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 订单ID */
    @Excel(name = "订单ID")
    private String orderId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private String goodsId;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private Integer goodsCount;

    /** 明细状态:
     * 0 未退款
1.退款申请
2.退款中
3.退款成功 */
    @Excel(name = "明细状态")
    private Integer status;

    /** 统一退单号 */
    @Excel(name = "统一退单号")
    private String refundNo;

    /** 退款时间 */
    @Excel(name = "退款时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    private GoodsMain goods;
}
