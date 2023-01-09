package com.ruoyi.framework.encrypt;

import com.ruoyi.common.encrypt.IEncryptor;
import com.ruoyi.common.encrypt.IEncryptorManager;
import com.ruoyi.framework.config.properties.EncryptFieldProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 加解密管理者
 *
 * @author 老马
 * @date 2023-01-06 09:19
 */
@Slf4j
@Component
public class EncryptorManager implements IEncryptorManager {
    @Resource
    private EncryptFieldProperties properties;
    @Resource
    private List<IEncryptor> encryptors;

    /**
     * 加密
     *
     * @param value 待加密字符串
     * @return 加密后的字符串
     */
    @Override
    public String encrypt(String value) {
        try {
            for(IEncryptor encryptor : encryptors) {
                if(encryptor.isMyAlgorithm(properties.getAlgorithm())) {
                    return encryptor.encrypt(value);
                }
            }
            //没有加解密执行者或者算法名找不到时，还是返回原字符串
            return value;
        }catch (Exception e) {
            log.error("字段加密异常,原样返回。异常信息：【{}】", e.getMessage());
            return value;
        }
    }

    /**
     * 解密
     *
     * @param value 待解密字符串
     * @return 解密后的字符串
     */
    @Override
    public String decrypt(String value)  {
        try {
            for(IEncryptor encryptor : encryptors) {
                if(encryptor.isMyAlgorithm(properties.getAlgorithm())) {
                    return encryptor.decrypt(value);
                }
            }
            //没有加解密执行者或者算法名找不到时，还是返回原字符串
            return value;
        }catch(Exception e) {
            log.error("字段解密异常,原样返回。异常信息：【{}】", e.getMessage());
            return value;
        }
    }
}
