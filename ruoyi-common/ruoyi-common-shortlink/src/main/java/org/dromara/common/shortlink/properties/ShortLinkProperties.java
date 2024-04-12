package org.dromara.common.shortlink.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 短链接 配置属性
 *
 * @author AprilWind
 */
@Data
@ConfigurationProperties(prefix = "shortlink")
public class ShortLinkProperties {

    /**
     * 短链接主机名或域名
     */
    private String host;

    /**
     * 错误页面的主机名或域名
     */
    private String erroHost;

}
