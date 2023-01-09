package com.ruoyi.framework.encrypt.encryptor;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.ruoyi.common.encrypt.IEncryptor;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.properties.EncryptFieldProperties;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * RSA算法实现
 *
 * @author 老马
 * @date 2023-01-06 09:37
 */
@Component
public class RsaEncryptor implements IEncryptor {

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
        return StringUtils.equalsIgnoreCase(algorithm, AlgorithmType.RSA.getAlgorithm());
    }

    /**
     * 公钥加密
     *
     * @param value 待加密字符串
     * @return 加密后的字符串
     * @throws Exception 抛出异常
     */
    @Override
    public String encrypt(String value) throws Exception {
        RSA rsa = new RSA(properties.getPrivateKey(), properties.getPublicKey());
        if(StringUtils.endsWithIgnoreCase(properties.getEncodeType(), IEncryptor.ENCODETYPE_HEX)) {
            return rsa.encryptHex(value, KeyType.PublicKey);
        }else {
            return rsa.encryptBase64(value, KeyType.PublicKey);
        }
    }

    /**
     * 私钥解密
     *
     * @param value 待解密字符串
     * @return 解密后的字符串
     * @throws Exception 抛出异常
     */
    @Override
    public String decrypt(String value) throws Exception {
        RSA rsa = new RSA(properties.getPrivateKey(), properties.getPublicKey());
        return rsa.decryptStr(value, KeyType.PrivateKey);
    }
}
