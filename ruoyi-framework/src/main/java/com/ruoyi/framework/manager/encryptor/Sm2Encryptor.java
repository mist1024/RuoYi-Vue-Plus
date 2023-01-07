package com.ruoyi.framework.manager.encryptor;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.properties.EncryptFieldProperties;
import com.ruoyi.framework.manager.encryptor.IEncryptor;
import org.springframework.stereotype.Component;

/**
 * sm2算法实现
 *
 * @author 老马
 * @date 2023-01-06 17:13
 */
@Component
public class Sm2Encryptor implements IEncryptor {
    /**
     * 是否为本加解密处理的算法名称
     *
     * @param algorithm 算法名称
     * @return 是否本类能处理的算法
     */
    @Override
    public Boolean isMyAlgorithm(String algorithm) {
        return StringUtils.equalsIgnoreCase(algorithm, AlgorithmType.SM2.getAlgorithm());
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
        SM2 sm2 = SmUtil.sm2(properties.getPrivateKey(), properties.getPublicKey());
        if(StringUtils.endsWithIgnoreCase(properties.getEncodeType(), IEncryptor.ENCODETYPE_HEX)) {
            return sm2.encryptHex(value, KeyType.PublicKey);
        }else {
            return sm2.encryptBase64(value, KeyType.PublicKey);
        }
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
        SM2 sm2 = SmUtil.sm2(properties.getPrivateKey(), properties.getPublicKey());
        return sm2.decryptStr(value, KeyType.PrivateKey);
    }
}
