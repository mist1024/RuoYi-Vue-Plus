package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工管理对象 ft_staff_info
 *
 * @author ft
 * @date 2020-11-19
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_staff_info")
public class FtStaffInfoDao implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 员工 id
     */
    @TableId(value = "staff_id")
    private Long staffId;

    /**
     * 科室 id
     */
    private Long departId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 岗位
     */
    @Excel(name = "岗位")
    private String post;

    /**
     * 角色
     */
    @Excel(name = "角色")
    private String role;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建日期
     */
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 启用标志
     */
    private Integer flag;

    /**
     * 补贴余额
     */
    @Excel(name = "补贴余额")
    private BigDecimal balance;
}
