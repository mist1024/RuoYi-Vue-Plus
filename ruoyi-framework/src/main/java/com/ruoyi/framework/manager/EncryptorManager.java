package com.ruoyi.framework.manager;

import com.ruoyi.framework.config.properties.EncryptFieldProperties;
import com.ruoyi.framework.manager.encryptor.IEncryptor;
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
public class EncryptorManager {
    @Resource
    private List<IEncryptor> encryptors;

    /**
     * 加密
     * @param value 待加密字符串
     * @param properties 加解密配置信息
     * @return 加密后的字符串
     * @throws Exception 抛出异常
     */
    public String encrypt(String value, EncryptFieldProperties properties) {
        try {
            for(IEncryptor encryptor : encryptors) {
                if(encryptor.isMyAlgorithm(properties.getAlgorithm())) {
                    return encryptor.encrypt(value, properties);
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
     * @param value 待解密字符串
     * @param properties 加解密配置信息
     * @return 解密后的字符串
     * @throws Exception 抛出异常
     */
    public String decrypt(String value, EncryptFieldProperties properties)  {
        try {
            for(IEncryptor encryptor : encryptors) {
                if(encryptor.isMyAlgorithm(properties.getAlgorithm())) {
                    return encryptor.decrypt(value, properties);
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
