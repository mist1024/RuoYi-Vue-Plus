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
 * 用户收货地址对象 app_user_address
 * 
 * @author ruoyi
 * @date 2021-01-16
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_user_address")
public class AppUserAddress implements Serializable {

private static final long serialVersionUID=1L;


    /** ID */
    @TableId(value = "id")
    private String id;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;

    /** 是否默认 */
    @Excel(name = "是否默认")
    private Integer isDefault;

    /** 手机号 */
    @Excel(name = "手机号")
    private String mobile;

    /** 收货人 */
    @Excel(name = "收货人")
    private String name;

    /** 省市县地址 */
    @Excel(name = "省市县地址")
    private String address;

    /** 省市县地址 */
    @Excel(name = "省市县地址")
    private String region;

    /** 删除标志 */
    private String delFlag;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
