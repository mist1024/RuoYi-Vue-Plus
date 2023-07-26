package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 user
 *
 * @author ruoyi
 * @date 2023-04-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 用户账号状态  1，未认定 2，认定中 ，3 未发卡，4 已发卡
     */
    private Long status;
    /**
     * 用户名
     */
    private String loginName;
    /**
     * 登陆密码
     */
    private String password;
    /**
     * 卡号
     */
    private String cardNumber;
    /**
     * 认定类型
     */
    private String type;
    /**
     * 卡有效期
     */
    private String validityDate;
    /**
     * 微信token
     */
    private String wxToken;
    /**
     * 登陆次数
     */
    private Long enterNumber;
    /**
     * 用户名称
     */
    private String userName;
    /**
     *
     */
    private Date lastTime;
    /**
     * 方向
     */
    private Long direction;
    /**
     * 用户限制  1，正常 2，禁用
     */
    private Long jurisdiction;
    /**
     * 求职意向
     */
    private String jobWanted;
    /**
     *
     */
    private Date registerTime;
    /**
     * 申报的微信token
     */
    private String wxToken1;
    /**
     * 身份证
     */
    private String cardId;

    /**
     * 用户类型
     */
    private String userType;

    private String companyId;

    private String apiKey;

}
