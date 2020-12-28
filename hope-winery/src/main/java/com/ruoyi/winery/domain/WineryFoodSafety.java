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
 * 食品安全详情对象 winery_food_safety
 * 
 * @author ruoyi
 * @date 2020-12-28
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("winery_food_safety")
public class WineryFoodSafety implements Serializable {

private static final long serialVersionUID=1L;


    /** 规格ID */
    @TableId(value = "id")
    private Long id;

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

    /** 生产许可证编号 */
    @Excel(name = "生产许可证编号")
    private String prodLicense;

    /** 产品标准号 */
    @Excel(name = "产品标准号")
    private String standardId;

    /** 厂名 */
    @Excel(name = "厂名")
    private String makerName;

    /** 厂址 */
    @Excel(name = "厂址")
    private String makerAddress;

    /** 厂家联系方式 */
    @Excel(name = "厂家联系方式")
    private String makerContact;

    /** 配料表 */
    @Excel(name = "配料表")
    private String ingredients;

    /** 保质期 */
    @Excel(name = "保质期")
    private Integer period;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 葡萄酒等级 */
    @Excel(name = "葡萄酒等级")
    private String wineLevel;

    /** 储藏方法
 */
    @Excel(name = "储藏方法")
    private String storageMode;

    /** 供应商
 */
    @Excel(name = "供应商")
    private String supplier;

    /** 生产日期 */
    @Excel(name = "生产日期" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date makeDate;
}
