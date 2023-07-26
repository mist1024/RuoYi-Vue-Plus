package com.ruoyi.common.core.domain.model;

import com.ruoyi.common.constant.UserConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户登录对象
 *
 * @author Lion Li
 */

@Data
public class LoginBody {

    /**
     * 用户名
     */
    @NotBlank(message = "{user.username.not.blank}",groups = {passwordLogin.class,smgLogin.class,forgetPasswordLogin.class,registerUser.class,userOpenLogin.class})
    @Length(min = UserConstants.USERNAME_MIN_LENGTH, max = UserConstants.USERNAME_MAX_LENGTH, message = "{user.username.length.valid}",groups = {passwordLogin.class,smgLogin.class,forgetPasswordLogin.class,registerUser.class})
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "{user.password.not.blank}",groups = {passwordLogin.class,forgetPasswordLogin.class,registerUser.class})
    @Pattern(regexp = UserConstants.PASSWORD_Pattern,message = "{the.password.is.not.within.the.specified.range}",groups = {passwordLogin.class,forgetPasswordLogin.class,registerUser.class})
    @Length(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH, message = "{user.password.length.valid}",groups = {passwordLogin.class,forgetPasswordLogin.class,registerUser.class})
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "{user.jcaptcha.not.blank}",groups = {smgLogin.class,forgetPasswordLogin.class,registerUser.class})
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;


    /**
     * 外部用户标识key
     */
    @NotBlank(message = "user.id.cannot.be.empty",groups ={userOpenLogin.class})
    private String apiKey;


    /**
     * 账号登录验证
     */
    public interface passwordLogin {}

    /**
     * 短信登录验证
     */
    public interface smgLogin {}

    /**
     * 忘记密码验证
     */
    public interface forgetPasswordLogin {}

    /**
     * 注册验证
     */
    public interface registerUser {}

    public interface userOpenLogin {}



}

