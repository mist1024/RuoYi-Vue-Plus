package org.dromara.common.encrypt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * api解密属性配置类
 * @author wdhcr
 */
@Data
@ConfigurationProperties(prefix = "api-decrypt")
public class ApiDecryptProperties {

    /**
     * 加密开关
     */
    private Boolean enabled;

    /**
     * 响应加密开关
     */
    private Boolean responseEnabled;

    /**
     * 头部标识
     */
    private String headerFlag;

    /**
     * 请求加密公钥
     */
    private String publicKey;

    /**
     * 请求加密私钥
     */
    private String privateKey;

    /**
     * 响应加密公钥
     */
    private String responsePublicKey;

    /**
     * 响应加密私钥
     */
    private String responsePrivateKey;

}
