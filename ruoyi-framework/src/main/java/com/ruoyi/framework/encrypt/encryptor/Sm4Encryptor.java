package com.ruoyi.framework.encrypt.encryptor;

import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.ruoyi.common.encrypt.IEncryptor;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.properties.EncryptFieldProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * sm4算法实现
 *
 * @author 老马
 * @date 2023-01-06 17:40
 */
@Component
public class Sm4Encryptor implements IEncryptor {

    @Resource
    private EncryptFieldProperties properties;

    /**
     * 是否为本加解密处理的算法名称
     *
     * @param algorithm 算法名称
     * @return 是否本类能处理的算法
     */
    @Override
    public Boolean isMyAlgorithm(String algorithm) {
        return StringUtils.equalsIgnoreCase(algorithm, AlgorithmType.SM4.getAlgorithm());
    }

    /**
     * 加密
     *
     * @param value      待加密字符串
     * @return 加密后的字符串
     * @throws Exception 抛出异常
     */
    @Override
    public String encrypt(String value)  throws Exception{
        SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", properties.getSecretKey().getBytes());
        if(StringUtils.endsWithIgnoreCase(properties.getEncodeType(), IEncryptor.ENCODETYPE_HEX)) {
            return sm4.encryptHex(value);
        }else {
            return sm4.encryptBase64(value);
        }
    }

    /**
     * 解密
     *
     * @param value      待解密字符串
     * @return 解密后的字符串
     * @throws Exception 抛出异常
     */
    @Override
    public String decrypt(String value) throws Exception{
        SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", properties.getSecretKey().getBytes());
        return sm4.decryptStr(value);
    }
}
