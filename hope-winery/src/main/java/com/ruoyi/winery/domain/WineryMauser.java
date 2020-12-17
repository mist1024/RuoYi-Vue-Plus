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
 * 小程序用户对象 winery_mauser
 * 
 * @author ruoyi
 * @date 2020-12-17
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("winery_mauser")
public class WineryMauser implements Serializable {

private static final long serialVersionUID=1L;


    /** 小程序userid */
    @Excel(name = "小程序userid")
    @TableId(value = "open_id")
    private String openId;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 手机号 */
    @Excel(name = "手机号")
    private String mobile;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** unionid */
    @Excel(name = "unionid")
    private String unionId;

    /** 创建时间 */
    @Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 租户id */
    @Excel(name = "租户id")
    private String tenantId;
}
