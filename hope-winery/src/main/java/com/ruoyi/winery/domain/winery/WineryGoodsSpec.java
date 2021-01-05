package com.ruoyi.winery.domain.winery;

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
 * 商品规格对象 winery_goods_spec
 * 
 * @author ruoyi
 * @date 2020-12-28
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("winery_goods_spec")
public class WineryGoodsSpec implements Serializable {

private static final long serialVersionUID=1L;


    /** 规格ID */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 更新者 */
    private String updateBy;

    /** 规格名称 */
    @Excel(name = "规格名称")
    private String specName;

    /** 规格说明 */
    @Excel(name = "规格说明")
    private String specDesc;

    /** 规格图片 */
    @Excel(name = "规格图片")
    private String specImg;

    /** 规格单价 */
    @Excel(name = "规格单价")
    private Long specPrice;

    /** 规格详细 */
    @Excel(name = "规格详细")
    private Long detailSpec;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;
}
