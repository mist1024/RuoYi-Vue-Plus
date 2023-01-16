package com.ruoyi.framework.encrypt.encryptor;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.ruoyi.common.encrypt.IEncryptor;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.enums.EncodeType;

/**
 * sm2算法实现
 *
 * @author 老马
 * @date 2023-01-06 17:13
 */
public class Sm2Encryptor implements IEncryptor {

    private SM2 sm2 = null;

    public Sm2Encryptor(String privateKey, String publicKey) {
        sm2 = SmUtil.sm2(Base64.decode(privateKey), Base64.decode(publicKey));
    }

    /**
     * 获得当前算法
     *
     * @return com.ruoyi.common.enums.AlgorithmType
     * @author 老马
     * @date 2023/1/11 11:18
     */
    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.SM2;
    }

    /**
     * 加密
     *
     * @param value      待加密字符串
     * @param encodeType 加密后的编码格式
     * @return java.lang.String
     * @throws Exception 抛出异常
     * @author 老马
     * @date 2023/1/10 16:38
     */
    @Override
    public String encrypt(String value, EncodeType encodeType) throws Exception {
        if(ObjectUtil.isNotNull(this.sm2)) {
            if (encodeType == EncodeType.HEX) {
                return sm2.encryptHex(value, KeyType.PublicKey);
            } else {
                return sm2.encryptBase64(value, KeyType.PublicKey);
            }
        }
        return value;
    }

    /**
     * 解密
     *
     * @param value      待加密字符串
     * @param encodeType 加密后的编码格式
     * @return java.lang.String
     * @throws Exception 抛出异常
     * @author 老马
     * @date 2023/1/10 16:38
     */
    @Override
    public String decrypt(String value, EncodeType encodeType) throws Exception {
        if(ObjectUtil.isNotNull(this.sm2)) {
            return this.sm2.decryptStr(value, KeyType.PrivateKey);
        }
        return value;
    }
}
