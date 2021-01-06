package com.ruoyi.winery.component;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.winery.config.wx.WxMiniProperties;
import com.ruoyi.winery.domain.winery.WineryMauser;
import com.ruoyi.winery.service.IWineryMauserService;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


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
