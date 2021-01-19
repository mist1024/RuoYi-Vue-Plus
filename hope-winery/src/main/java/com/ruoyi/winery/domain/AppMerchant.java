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
 * 商户对象 app_merchant
 * 
 * @author ruoyi
 * @date 2021-01-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_merchant")
public class AppMerchant implements Serializable {

private static final long serialVersionUID=1L;


    /** ID */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /** 商户名称 */
    @Excel(name = "商户名称")
    private String mchName;

    /** 副标题 */
    @Excel(name = "副标题")
    private String subtitle;

    /** 图标 */
    @Excel(name = "图标")
    private String avatar;

    /** 介绍 */
    @Excel(name = "介绍")
    private String mchDesc;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;
}
