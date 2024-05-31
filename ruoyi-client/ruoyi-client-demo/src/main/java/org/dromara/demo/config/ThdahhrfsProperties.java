package org.dromara.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "client.thdahhrfs")
public class ThdahhrfsProperties {

    private String appid;

    /**
     * 使用分配的Appid通过加密后生成
     */
    private String appSecret;

    /**
     * SM2公钥
     */
    private String publicKey;

    /**
     * SM2私钥
     */
    private String privateKey;

}
