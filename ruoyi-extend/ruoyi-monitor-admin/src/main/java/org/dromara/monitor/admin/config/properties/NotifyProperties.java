package org.dromara.monitor.admin.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 通知配置
 *
 * @author AprilWind
 */
@Data
@ConfigurationProperties(prefix = "notify")
@Configuration
public class NotifyProperties {

    /**
     * 邮件设置
     */
    private Mail mail;

    /**
     * WebHooks 设置
     */
    private WebHook webHook;

    @Data
    @NoArgsConstructor
    public static class Mail {

        /**
         * 发送开关
         */
        private Boolean enabled = false;

        /**
         * 收件人
         */
        private String to;

        /**
         * 主题
         */
        private String subject;

        /**
         * 邮件模版
         */
        private String template;

    }

    @Data
    @NoArgsConstructor
    public static class WebHook {

        /**
         * 发送开关
         */
        private Boolean enabled = false;

        /**
         * 0默认 1密码 2签名密钥
         */
        private String type;

        /**
         * Post地址
         */
        private String url;

        /**
         * WebHook发送模版
         */
        private String template;

    }

}
