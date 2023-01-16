package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 编码类型
 *
 * @author 老马
 * @date 2023-01-11 11:39
 */
@Getter
@AllArgsConstructor
public enum EncodeType {
    /**
     * base64编码
     */
    BASE64("base64"),

    /**
     * 16进制编码
     */
    HEX("hex");

    private final String encode;
}
