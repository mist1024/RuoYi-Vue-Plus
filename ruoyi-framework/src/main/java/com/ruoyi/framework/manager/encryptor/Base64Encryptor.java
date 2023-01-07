package com.ruoyi.framework.manager.encryptor;

import cn.hutool.core.codec.Base64;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.properties.EncryptFieldProperties;
import com.ruoyi.framework.manager.encryptor.IEncryptor;
import org.springframework.stereotype.Component;

/**
 * Base64算法实现。不建议在生产环境使用
 *
 * @author 老马
 * @date 2023-01-06 10:00
 */
@Component
public class Base64Encryptor implements IEncryptor {
    /**
     * 是否为本加解密处理的算法名称
     *
     * @param algorithm 算法名称
     * @return 是否本类能处理的算法
     */
    @Override
    public Boolean isMyAlgorithm(String algorithm) {
        return StringUtils.equalsIgnoreCase(algorithm, AlgorithmType.BASE64.getAlgorithm());
    }

    /**
     * 加密
     *
     * @param value      待加密字符串
     * @param properties 加解密配置信息
     * @return 加密后的字符串
     * @throws Exception 抛出异常
     */
    @Override
    public String encrypt(String value, EncryptFieldProperties properties) throws Exception {
        return Base64.encode(value);
    }

    /**
     * 解密
     *
     * @param value      待解密字符串
     * @param properties 加解密配置信息
     * @return 解密后的字符串
     * @throws Exception 抛出异常
     */
    @Override
    public String decrypt(String value, EncryptFieldProperties properties) throws Exception {
        return Base64.decodeStr(value);
    }
}
