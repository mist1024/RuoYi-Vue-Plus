package com.ruoyi.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 请求加解密属性配置类
 * @author wdhcr
 */
@Data
@Component
@ConfigurationProperties(prefix = "request-encryptor")
public class RequestEncryptProperties {

    /**
     * 加密开关
     */
    private Boolean enable;

    /**
     * 头部标识
     */
    private String headerFlag;


    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;
}
