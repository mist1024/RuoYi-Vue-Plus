package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 算法名称
 *
 * @author 老马
 */
@Getter
@AllArgsConstructor
public enum AlgorithmType {
    /**
     * base64
     */
    BASE64("com.ruoyi.framework.encrypt.encryptor.Base64Encryptor"),

    /**
     * aes
     */
    AES("com.ruoyi.framework.encrypt.encryptor.AesEncryptor"),

    /**
     * rsa
     */
    RSA("com.ruoyi.framework.encrypt.encryptor.RsaEncryptor"),

    /**
     * sm2
     */
    SM2("com.ruoyi.framework.encrypt.encryptor.Sm2Encryptor"),

    /**
     * sm4
     */
    SM4("com.ruoyi.framework.encrypt.encryptor.Sm4Encryptor");

    private final String clazz;
}
