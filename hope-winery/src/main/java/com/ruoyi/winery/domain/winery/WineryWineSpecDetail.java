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
 * 葡萄酒规格详情对象 winery_wine_spec_detail
 * 
 * @author ruoyi
 * @date 2020-12-28
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("winery_wine_spec_detail")
public class WineryWineSpecDetail implements Serializable {

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

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 净含量 */
    @Excel(name = "净含量")
    private Integer capacity;

    /** 采摘年份
 */
    @Excel(name = "采摘年份")
    private String year;

    /** 酒精度 */
    @Excel(name = "酒精度")
    private String alcohol;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 葡萄酒种类 */
    @Excel(name = "葡萄酒种类")
    private String wineType;

    /** 葡萄酒等级 */
    @Excel(name = "葡萄酒等级")
    private String wineLevel;

    /** 葡萄种类 */
    @Excel(name = "葡萄种类")
    private String grape;

    /** 糖分 */
    @Excel(name = "糖分")
    private String sugarContent;

    /** 包装方式 */
    @Excel(name = "包装方式")
    private String packingType;

    /** 醒酒时间 */
    @Excel(name = "醒酒时间")
    private String decanteDuration;

    /** 支数 */
    @Excel(name = "支数")
    private Integer count;

    /** 香味 */
    @Excel(name = "香味")
    private String aroma;
}
