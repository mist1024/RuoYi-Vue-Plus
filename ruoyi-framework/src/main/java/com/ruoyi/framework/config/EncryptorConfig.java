package com.ruoyi.framework.config;

import com.ruoyi.framework.config.properties.EncryptorProperties;
import com.ruoyi.framework.encrypt.EncryptorManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 加解密配置
 *
 * @author 老马
 * @date 2023-01-11 10:03
 */
@Configuration
public class EncryptorConfig {

    @Bean
    @ConditionalOnProperty(value = "mybatis-encryptor.enabled", havingValue = "true")
    public EncryptorManager mybatisCryptHandler(EncryptorProperties properties) {
        EncryptorManager encryptorManager = new EncryptorManager();
        encryptorManager.registAndGetEncryptor(properties);
        return encryptorManager;
    }
}
