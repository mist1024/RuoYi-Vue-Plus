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
    BASE64("base64"),
    /**
     * aes
     */
    AES("aes"),
    /**
     * rsa
     */
    RSA("rsa"),
    /**
     * sm2
     */
    SM2("sm2"),
    /**
     * sm4
     */
    SM4("sm4");

    private final String algorithm;
}
