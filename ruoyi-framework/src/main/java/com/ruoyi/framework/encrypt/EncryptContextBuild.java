package com.ruoyi.framework.encrypt;

import cn.hutool.extra.spring.SpringUtil;
import com.ruoyi.common.annotation.EncryptField;
import com.ruoyi.common.encrypt.EncryptContext;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.enums.EncodeType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.properties.EncryptorProperties;

import java.lang.reflect.Field;

/**
 * 构建加解密
 * @author Yang Shuai
 * {@code @create} 2023/3/24:15:50
 * {@code @desc} |
 **/
public class EncryptContextBuild {

    /**
     * 构建
     *
     * @param field 字段
     * @return {@link EncryptContext}
     */
    public static EncryptContext build(Field field) {
        EncryptorProperties defaultProperties = SpringUtil.getBean(EncryptorProperties.class);
        EncryptField encryptField = field.getAnnotation(EncryptField.class);
        EncryptContext encryptContext = new EncryptContext();
        encryptContext.setAlgorithm(encryptField.algorithm() == AlgorithmType.DEFAULT ? defaultProperties.getAlgorithm() : encryptField.algorithm());
        encryptContext.setEncode(encryptField.encode() == EncodeType.DEFAULT ? defaultProperties.getEncode() : encryptField.encode());
        encryptContext.setPassword(StringUtils.isBlank(encryptField.password()) ? defaultProperties.getPassword() : encryptField.password());
        encryptContext.setPrivateKey(StringUtils.isBlank(encryptField.privateKey()) ? defaultProperties.getPrivateKey() : encryptField.privateKey());
        encryptContext.setPublicKey(StringUtils.isBlank(encryptField.publicKey()) ? defaultProperties.getPublicKey() : encryptField.publicKey());
        return encryptContext;
    }
}
