package com.ruoyi.common.encrypt;

/**
 * 加解密执行者
 *
 * @author 老马
 * @date 2023-01-06 09:20
 */
public interface IEncryptor {
    /**
     * 编码类型-16进制
     */
    String ENCODETYPE_HEX = "hex";

    /**
     * 是否为本加解密处理的算法名称
     *
     * @param algorithm 算法名称
     * @return 是否本类能处理的算法
     */
    Boolean isMyAlgorithm(String algorithm);

    /**
     * 加密
     *
     * @param value 待加密字符串
     * @return 加密后的字符串
     * @throws Exception 抛出异常
     */
    String encrypt(String value) throws Exception;

    /**
     * 解密
     *
     * @param value 待解密字符串
     * @return 解密后的字符串
     * @throws Exception 抛出异常
     */
    String decrypt(String value) throws Exception;

}
