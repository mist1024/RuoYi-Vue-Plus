package com.ruoyi.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tencent-cos")
public class CosProperties {

    String bucketName;
    String bucketUrl;
    String secretId;
    String secretKey;
    String region;
}
