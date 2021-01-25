package com.ruoyi.winery.component;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.winery.config.wx.WxMiniProperties;
import com.ruoyi.winery.domain.winery.WineryMauser;
import com.ruoyi.winery.service.IWineryMauserService;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ruoyi.winery.define.MiniDefine.*;


/**
 * @author tottimctj
 * @since 2020-12-15
 */

@Component
@Slf4j
public class MiniComponent {

    @Autowired
    IWineryMauserService wineryMauserService;

    @Autowired
    WxMaService wxMaService;

    @Autowired
    private RedisCache redisCache;


    @Autowired
    private WxMiniProperties wxMiniProperties;

    @Autowired
    private WxPayService wxPayService;


    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private ISysUserService userService;


    public WxMaJscode2SessionResult login(String code) throws WxErrorException {

        WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);

        WineryMauser user = wineryMauserService.getById(sessionInfo.getOpenid());
        String key = sessionInfo.getOpenid();
        redisCache.setCacheObject(key, sessionInfo.getSessionKey(), 7200, TimeUnit.SECONDS);
        if (user == null) {
            user = new WineryMauser();
            user.setOpenId(sessionInfo.getOpenid());
            log.info("新增user:{}", user);
        }

        if (StrUtil.isNotBlank(sessionInfo.getUnionid())) {
            user.setUnionId(sessionInfo.getUnionid());
        }
        wineryMauserService.saveOrUpdate(user);

        return sessionInfo;
    }

    public AjaxResult registration(String openid, String mobile) {


        SysUser user = new SysUser();

        user.setUserName(openid);
        user.setPhonenumber(mobile);

        user.setNickName(mobile);
        user.setDeptId(MINI_DEPTID);
        user.setPassword(MINI_DEFUALT_PASSWORD);
        user.setRoleIds(new Long[]{MINI_DEFUALT_ROLEID});
        user.setPostIds(new Long[]{MINI_DEFUALT_POSTID});


        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户" + user.getUserName() + "失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(MINI_MANAGE_USER);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return userService.insertUser(user) > 0 ? AjaxResult.success(user) : AjaxResult.error();


    }

    /**
     * 小程序登录
     *
     * @param openId
     * @return
     */
    public String loginByMini(String openId) {
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(openId, MINI_DEFUALT_PASSWORD));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(openId, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(openId, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(openId, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);

    }

    /**
     * 获取手机号
     *
     * @param json 微信加密信息
     * @return
     */
    public String getMobile(JSONObject json) {

        String openid = json.getStr("openid");
        WineryMauser user = wineryMauserService.getById(openid);

        JSONObject detail = json.getJSONObject("detail");
        String encryptedData = detail.getStr("encryptedData");
        String iv = detail.getStr("iv");


        String sessionKey = redisCache.getCacheObject(openid);

        if (StrUtil.isBlank(sessionKey)) {
            return null;
        }

        WxMaPhoneNumberInfo mobile = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        user.setMobile(mobile.getPhoneNumber());

        wineryMauserService.saveOrUpdate(user);

        return mobile.getPhoneNumber();
    }

    public JSONObject getRealAuthJson(String openid) {


        JSONObject json = new JSONObject();
        json.set("api_version", "1.0");
        json.set("mch_id", wxMiniProperties.getMchId());
        json.set("appid", wxMiniProperties.getAppId());
        json.set("scope", "pay_identity");
        json.set("response_type", "code");
//        json.set("openid", "okT7b4rOqy7FkJ_DxuBhyRPo0sGA");
        json.set("openid", openid);
        json.set("sign_type", "HMAC-SHA256");
        json.set("nonce_str", IdUtil.fastSimpleUUID());

        Map<String, String> params = json.toBean(HashMap.class);


        json.set("sign", SignUtils.createSign(params, "HMAC-SHA256", wxMiniProperties.getMchKey(), (String[]) null));


        return json;
    }

}
