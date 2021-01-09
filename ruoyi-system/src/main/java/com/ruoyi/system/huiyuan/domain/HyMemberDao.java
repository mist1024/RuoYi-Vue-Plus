package com.ruoyi.system.huiyuan.domain;

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
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * 会员对象 hy_member
 * 
 * @author ryo
 * @date 2021-01-09
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("hy_member")
public class HyMemberDao implements Serializable {

private static final long serialVersionUID=1L;


    /** id */
    @TableId(value = "id")
    private Long id;

    /** 手机 */
    @Excel(name = "手机")
    private String tel;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 所属项目 */
    @Excel(name = "所属项目")
    private Integer projType;

    /** 积分 */
    @Excel(name = "积分")
    private Long points;

    /** 代金券 */
    @Excel(name = "代金券")
    private Long voucher;

    /** 余额 */
    @Excel(name = "余额")
    private BigDecimal balance;

    /** 智能设备 */
    @Excel(name = "智能设备")
    private String device;

    /** 会员 URL */
    @Excel(name = "会员 URL")
    private String memberUrl;

    /** 创建时间 */
    @Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /** 更新时间 */
    @Excel(name = "更新时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    /** 项目 id */
    @Excel(name = "项目 id")
    private Long projId;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
