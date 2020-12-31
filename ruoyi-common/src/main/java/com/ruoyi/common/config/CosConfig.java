package com.ruoyi.common.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tottimctj
 * @since 2020-12-28
 */
@Configuration
@EnableConfigurationProperties({CosProperties.class})
public class CosConfig {

    @Autowired
    private CosProperties properties;

    @Bean(name = "cosCredentials")
    public COSCredentials cosCredentials() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = properties.secretId;
        String secretKey = properties.secretKey;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        return cred;
    }



    @Bean(name = "cosClientConfig")
    public ClientConfig cosClientConfig() {
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(properties.region);
        ClientConfig clientConfig = new ClientConfig(region);
        return clientConfig;
    }

}
