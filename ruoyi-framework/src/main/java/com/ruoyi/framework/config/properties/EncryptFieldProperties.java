package com.ruoyi.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 加密字段属性配置
 *
 * @author 老马
 * @date 2023-01-06 08:57
 */
@Data
@Component
@ConfigurationProperties(prefix = "mybatis-plus.encrypt-field")
public class EncryptFieldProperties {
    /**
     * 算法名称
     */
    private String algorithm;

    /**
     * 字符串的编码方式
     */
    private String encodeType;

    /**
     * 非对称加密算法的公钥
     */
    private String publicKey;

    /**
     * 非对称加密算法的私钥
     */
    private String privateKey;

    /**
     * AES和SM4 的 秘钥
     */
    private String secretKey;

    /**
     * AES 的 ivKey
     */
    private String ivKey;
}
