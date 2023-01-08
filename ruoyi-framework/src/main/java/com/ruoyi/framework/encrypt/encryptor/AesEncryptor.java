package com.ruoyi.framework.encrypt.encryptor;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.ruoyi.common.encrypt.IEncryptor;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.properties.EncryptFieldProperties;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES算法实现
 *
 * @author 老马
 * @date 2023-01-06 11:39
 */
@Component
public class AesEncryptor implements IEncryptor {

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
        return StringUtils.equalsIgnoreCase(algorithm, AlgorithmType.AES.getAlgorithm());
    }

    /**
     * AES加密
     *
     * @param value 待加密字符串
     * @return 加密后的字符串
     * @throws Exception 抛出异常
     */
    @Override
    public String encrypt(String value) throws Exception {

        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,
            new SecretKeySpec(HexUtil.decodeHex(properties.getSecretKey()), StringUtils.toRootUpperCase(properties.getAlgorithm())),
            new IvParameterSpec(HexUtil.decodeHex(properties.getIvKey())));
        if(StringUtils.endsWithIgnoreCase(properties.getEncodeType(), IEncryptor.ENCODETYPE_HEX)) {
            return aes.encryptHex(value);
        }else {
            return aes.encryptBase64(value);
        }
    }

    /**
     * AES解密
     *
     * @param value 待解密字符串
     * @return 解密后的字符串
     * @throws Exception 抛出异常
     */
    @Override
    public String decrypt(String value) throws Exception {
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,
            new SecretKeySpec(HexUtil.decodeHex(properties.getSecretKey()), properties.getAlgorithm()),
            new IvParameterSpec(HexUtil.decodeHex(properties.getIvKey())));
        return aes.decryptStr(value);
    }
}
