package com.ruoyi.common.encrypt;

/**
 * 加解密管理者接口
 *
 * @author 老马
 * @date 2023-01-08 12:01
 */
public interface IEncryptorManager {
    /**
     * 调用具体的执行者进行加密
     *
     * @param value 待加密字符串
     * @return 加密后的字符串
     */
    String encrypt(String value);

    /**
     * 调用具体的执行者进行解密
     *
     * @param value 待解密字符串
     * @return 解密后的字符串
     */
    String decrypt(String value);
}
