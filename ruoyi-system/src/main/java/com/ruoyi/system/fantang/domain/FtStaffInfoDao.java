package com.ruoyi.system.fantang.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工管理对象 ft_staff_info
 *
 * @author ft
 * @date 2020-11-24
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ft_staff_info")
public class FtStaffInfoDao {

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
     * 科室名
     */
    @TableField(exist = false)
    private String departName;

    /**
     * 科室编号
     */
    @TableField(exist = false)
    private String departCode;

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
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 启用标志
     */
    @Excel(name = "启用标志")
    private Boolean flag;

    /**
     * 补贴余额
     */
    @Excel(name = "补贴余额")
    private BigDecimal balance;

    /**
     * 员工类别
     */
    private Long staffType;

    /**
     * 所属公司
     */
    private String corpName;

    /**
     * 照片
     */
    private String pictureUrl;

    /**
     * 报餐科室列表
     */
    private String deptList;

    /**
     * 二维码
     */
    private String qrCode;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 手机号码
     */
    private String tel;

    private String token;

    private Boolean loginFlag;

    private String expired;

    @TableField(exist = false)
    private Boolean giveOutFlag = true;

    /**
     * 人脸设备里的员工 id
     */
    private Long personId;
}