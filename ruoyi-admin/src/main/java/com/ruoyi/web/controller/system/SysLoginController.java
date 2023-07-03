package com.ruoyi.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.EmailLoginBody;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.SmsLoginBody;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StrUtils;
import com.ruoyi.common.utils.redis.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.sms.core.SmsTemplate;
import com.ruoyi.sms.core.TelecomSendMsg;
import com.ruoyi.sms.entity.SmsResult;
import com.ruoyi.system.domain.vo.RouterVo;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.SysLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录验证
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
public class SysLoginController {

    private final SysLoginService loginService;
    private final ISysMenuService menuService;
    private final ISysUserService userService;


    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/login")
    public R<Map<String, Object>> login(
//        @Validated({LoginBody.passwordLogin.class})
        @RequestBody LoginBody loginBody) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
            loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }


    /**
     * 短信登录
     *
     * @param smsLoginBody 登录信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/smsLogin")
    public R<Map<String, Object>> smsLogin(
        @Validated({LoginBody.smgLogin.class})
        @RequestBody SmsLoginBody smsLoginBody) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.smsLogin(smsLoginBody.getUsername(), smsLoginBody.getVerificationCode());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 邮件登录
     *
     * @param body 登录信息
     * @return 结果
     */
    @PostMapping("/emailLogin")
    public R<Map<String, Object>> emailLogin(@Validated @RequestBody EmailLoginBody body) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.emailLogin(body.getEmail(), body.getEmailCode());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 小程序登录(示例)
     *
     * @param xcxCode 小程序code
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/xcxLogin")
    public R<Map<String, Object>> xcxLogin(@NotBlank(message = "{xcx.code.not.blank}") String xcxCode) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.xcxLogin(xcxCode);
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 退出登录
     */
    @SaIgnore
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok("退出成功");
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R<Map<String, Object>> getInfo() {
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUser user = userService.selectUserById(loginUser.getUserId());
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("user", user);
        ajax.put("roles", loginUser.getRolePermission());
        ajax.put("permissions", loginUser.getMenuPermission());
        return R.ok(ajax);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = LoginHelper.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return R.ok(menuService.buildMenus(menus));
    }

//----------------------------------------------------客户端-------------------------------------------------------------
    /**
     * 登录方法
     * @param loginBody 登录信息
     * @return 结果
     */
    @SaIgnore
    @RateLimiter(count = 2, time = 10)
    @PostMapping("/userLogin")
    public R userLogin(
//        @Validated({LoginBody.passwordLogin.class})
        @RequestBody LoginBody loginBody) {
        // 生成令牌
        return R.ok("登录成功",loginService.userLogin(loginBody.getUsername(), loginBody.getPassword()));

    }

    /**
     * 手机号登录
     * @param loginBody
     * @return
     */
    @SaIgnore
//    @RateLimiter(count = 2, time = 10)
    @PostMapping("/userSmsLogin")
    public R<Map<String, Object>> userSmsLogin(@Validated(LoginBody.smgLogin.class) @RequestBody LoginBody loginBody) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.userSmsLogin(loginBody.getUsername(), loginBody.getCode());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 客户端忘记密码修改
     * @param loginBody
     * @return
     */
    @SaIgnore
    @RateLimiter(count = 1, time = 10)
    @PostMapping("/userUpdatePwd")
    public R<?> userUpdatePwd(@Validated(LoginBody.forgetPasswordLogin.class) @RequestBody LoginBody loginBody) {
        return loginService.userUpdatePwd(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode());
    }

    /**
     * 用户注册
     * @param loginBody
     * @return
     */
    @SaIgnore
    @RateLimiter(count = 1, time = 10)
    @PostMapping("/userRegister")
    public R<?> userRegister(@Validated(LoginBody.registerUser.class) @RequestBody LoginBody loginBody) {
        return  loginService.userRegister(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode());
    }


    /**
     * 忘记密码发送短信Aliyun
     * @param phones     电话号
     */
//    @RateLimiter(count = 1)
    @SaIgnore
    @GetMapping("/forgotSendAliYun")
    public R<?> forgotSendAliYun(String phones) {
        boolean matches = phones.matches("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");
        if (!matches) {
            return R.fail("手机号格式不正确");
        }
       return sendMsg(phones,CacheConstants.RORGOT_PASSWORD_SEND_MSG+phones,"forgetPwd");
    }

    /**
     * 注册用户发送验证码
     * @param phones
     * @return
     */
//    @RateLimiter(count = 1,time = 20)
    @SaIgnore
    @GetMapping("/registerSendAliYun")
    public R<?> registerSendAliYun(String phones) {
        boolean matches = phones.matches("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");
        if (!matches) {
            return R.fail("手机号格式不正确");
        }
        return sendMsg(phones,CacheConstants.REGISTER_SEND_MSG+phones,"register");
    }

    /**
     * 短信登录发送验证码
     * @param phones
     * @return
     */
//    @RateLimiter(count = 1,time = 20)
    @SaIgnore
    @GetMapping("/smsLoginSendAliYun")
    public R<?> smsLoginSendAliYun(String phones) {
        boolean matches = phones.matches("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");
        if (!matches) {
            return R.fail("手机号格式不正确");
        }
        return sendMsg(phones,CacheConstants.SMS_LOGIN_SEND_MSG+phones,"login");
    }

    public R<?> sendMsg(String phones,String key ,String type){
        //1.获取redis中是否存在该key
        Boolean aBoolean = RedisUtils.hasKey(key + phones);
        if (aBoolean) {
            return R.fail("当前账号验证码已发送,2分钟内有效,请勿再次点击");
        } else {
            TelecomSendMsg smsTemplate = SpringUtils.getBean(TelecomSendMsg.class);
            Map<String, String> map = new HashMap<>(1);
            String code = StrUtils.getRandomString(6);
            map.put("code",code);
            SmsResult send = smsTemplate.telecomSendCode(phones, code, type);
            if (send.isSuccess()){
                RedisUtils.setCacheObject(key + phones, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
                return R.ok();
            }
            System.out.println(code);
            return R.fail(send.getMessage());
        }
    }

    @SaIgnore
    @RateLimiter(count = 2, time = 10)
    @PostMapping("/userOpenLogin")
    public R userOpenLogin(
        @Validated({LoginBody.userOpenLogin.class})
        @RequestBody LoginBody loginBody) {
        // 生成令牌
        return R.ok("登录成功",loginService.userOpenLogin(loginBody.getUsername(), loginBody.getApiKey()));
    }
}
