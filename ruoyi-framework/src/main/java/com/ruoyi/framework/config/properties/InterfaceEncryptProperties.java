package com.ruoyi.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ltyzzz
 * @email ltyzzz2000@gmail.com
 * @date 2023/4/27 16:55
 */
@Data
@Component
@ConfigurationProperties(prefix = "interface-encryptor")
public class InterfaceEncryptProperties {

    /**
     * 过滤开关
     */
    private Boolean enable;

    /**
     * 密钥
     */
    private String secret;
}
