package org.dromara.common.oss.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * s3 Crt 配置属性
 *
 * @author AprilWind
 */
@Data
@ConfigurationProperties(prefix = "s3crt")
public class S3CrtProperties {

    /**
     * 读取后立即删除临时文件
     */
    Boolean delTempFilePath = true;

    /**
     * 校验和验证是否启用，默认为关闭状态
     */
    Boolean checksumValidationEnabled = false;

    /**
     * 是否启用分块编码，默认为关闭状态
     */
    Boolean chunkedEncodingEnabled = false;

    /**
     * 上传分块的大小，默认为 10 MB（10 * 1024 * 1024 字节）
     */
    Long uploadPartSize = 10 * 1024 * 1024L;

    /**
     * 目标吞吐量（以 Gbps 为单位），默认为 20.0 Gbps
     */
    Double targetThroughputInGbps = 20.0;

}
