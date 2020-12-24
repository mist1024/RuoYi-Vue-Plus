package com.ruoyi.winery.config.wx;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kino
 * @create 2019-07-23 14:53
 */
@Configuration
@EnableConfigurationProperties({WxMiniProperties.class})
public class WxPayConfiguration {
    private WxMiniProperties miniProperties;

    @Autowired
    public WxPayConfiguration(WxMiniProperties miniProperties) {
        this.miniProperties = miniProperties;
    }

    @Bean
    @ConditionalOnMissingBean(name = "wxServiceMini") //微信小程序
    public WxPayService wxServiceMini() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(this.miniProperties.getAppId()));
        payConfig.setMchId(StringUtils.trimToNull(this.miniProperties.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(this.miniProperties.getMchKey()));
        payConfig.setSubAppId(StringUtils.trimToNull(this.miniProperties.getSubAppId()));
        payConfig.setSubMchId(StringUtils.trimToNull(this.miniProperties.getSubMchId()));
        payConfig.setKeyPath(StringUtils.trimToNull(this.miniProperties.getKeyPath()));
        payConfig.setNotifyUrl(StringUtils.trimToNull(this.miniProperties.getNotifyUrl()));
        payConfig.setTradeType(StringUtils.trimToNull(this.miniProperties.getTradeType()));

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

}
