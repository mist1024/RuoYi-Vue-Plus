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
     * 客户端使用租户域名生成链接
     */
    private Boolean enabled = false;

    /**
     * 访问前缀 （开启时启用，可不填写，能访问到后端即可）
     */
    private String api;

    /**
     * 短链接主机名或域名
     */
    private String address;

    /**
     * 错误页面的主机名或域名
     */
    private String errorAddress="/error";

}
