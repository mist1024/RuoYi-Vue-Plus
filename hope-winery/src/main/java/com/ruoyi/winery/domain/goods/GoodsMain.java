package com.ruoyi.winery.domain.goods;

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
 * 商品信息对象 winery_goods
 * 
 * @author ruoyi
 * @date 2020-12-28
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("goods_main")
public class GoodsMain implements Serializable {

private static final long serialVersionUID=1L;


    /** 商品ID */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String goodsName;

    /** 商品简称 */
    @Excel(name = "商品简称")
    private String goodsAlias;

    /** 商品类型 */
    @Excel(name = "商品类型")
    private String goodsType;

    /** 关联规格 */
    @Excel(name = "关联规格")
    private String goodsSpec;

    /** 商品说明 */
    @Excel(name = "商品说明")
    private String goodsDesc;

    /** 商品封面 */
    @Excel(name = "商品封面")
    private String goodsFaceImg;

    /** 商品图片 */
    @Excel(name = "商品图片")
    private String goodsImg;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /**
     * 状态
     */
    private Integer state;
}
