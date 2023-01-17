package com.ruoyi.framework.encrypt.encryptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import com.ruoyi.common.encrypt.EncryptContext;
import com.ruoyi.common.encrypt.IEncryptor;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.enums.EncodeType;

import java.nio.charset.StandardCharsets;

/**
 * sm4算法实现
 *
 * @author 老马
 * @date 2023-01-06 17:40
 */
public class Sm4Encryptor implements IEncryptor {

    private SM4 sm4 = null;

    /**
     * 获得当前算法
     *
     * @return com.ruoyi.common.enums.AlgorithmType
     * @author 老马
     * @date 2023/1/11 11:18
     */
    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.SM4;
    }

    /**
     * 初始化加密者
     *
     * @param context 加密上下文
     * @throws Exception 抛出异常
     * @author 老马
     * @date 2023/1/17 09:01
     */
    @Override
    public void init(EncryptContext context) throws Exception {
        String password = context.getPassword();
        if (StrUtil.isBlank(password)) {
            throw new RuntimeException("sm4没有获得秘钥信息");
        }
        // sm4算法的秘钥要求是16位长度
        if (16 != password.length()) {
            throw new RuntimeException("sm4秘钥长度应该为16位，实际为" + password.length() + "位");
        }
        this.sm4 = SmUtil.sm4(password.getBytes(StandardCharsets.UTF_8));
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
        if (ObjectUtil.isNotNull(this.sm4)) {
            if (encodeType == EncodeType.HEX) {
                return sm4.encryptHex(value);
            } else {
                return sm4.encryptBase64(value);
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
        if (ObjectUtil.isNotNull(this.sm4)) {
            return this.sm4.decryptStr(value);
        }
        return value;
    }
}
