package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】业务对象 user
 *
 * @author ruoyi
 * @date 2023-04-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UserBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户账号状态  1，未认定 2，认定中 ，3 未发卡，4 已发卡
     */
    @NotNull(message = "用户账号状态  1，未认定 2，认定中 ，3 未发卡，4 已发卡不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String loginName;

    /**
     * 登陆密码
     */
    @NotBlank(message = "登陆密码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String password;

    /**
     * 卡号
     */
    @NotBlank(message = "卡号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cardNumber;

    /**
     * 认定类型
     */
    @NotBlank(message = "认定类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 卡有效期
     */
    @NotBlank(message = "卡有效期不能为空", groups = { AddGroup.class, EditGroup.class })
    private String validityDate;

    /**
     * 微信token
     */
    @NotBlank(message = "微信token不能为空", groups = { AddGroup.class, EditGroup.class })
    private String wxToken;

    /**
     * 登陆次数
     */
    @NotNull(message = "登陆次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long enterNumber;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date lastTime;

    /**
     * 方向
     */
    @NotNull(message = "方向不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long direction;

    /**
     * 用户限制  1，正常 2，禁用
     */
    @NotNull(message = "用户限制  1，正常 2，禁用不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long jurisdiction;

    /**
     * 求职意向
     */
    @NotBlank(message = "求职意向不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jobWanted;

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date registerTime;

    /**
     * 申报的微信token
     */
    @NotBlank(message = "申报的微信token不能为空", groups = { AddGroup.class, EditGroup.class })
    private String wxToken1;

    /**
     * 身份证
     */
    @NotBlank(message = "身份证不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cardId;

    /**
     * 用户类型
     */
    private String userType;

    private String companyId;


}
