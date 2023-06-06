package org.dromara.common.sms.config;

import org.dromara.common.sms.config.properties.SmsProperties;
import org.dromara.common.sms.core.Sms4jSmsTemplate;
import org.dromara.common.sms.core.SmsTemplate;
import org.dromara.sms4j.autoimmit.config.SmsAutowiredConfig;
import org.dromara.sms4j.autoimmit.utils.SpringUtil;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 短信配置类
 *
 * @author Lion Li
 * @version 4.2.0
 */
@AutoConfiguration
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnProperty(value = "sms.enabled", havingValue = "true")
@ConditionalOnClass(org.dromara.sms4j.autoimmit.config.SmsAutowiredConfig.class)
public class SmsConfig {

    @Bean
    public SpringUtil springUtil(DefaultListableBeanFactory defaultListableBeanFactory) {
        return new SpringUtil(defaultListableBeanFactory);
    }

    /**
     * 主要配置注入 确保springUtil注入后再注入
     */
    @Bean(initMethod = "init")
    public SmsAutowiredConfig smsAutowiredConfig(SpringUtil springUtil) {
        return new SmsAutowiredConfig(springUtil);
    }

    @Bean
    public SmsTemplate sms4jSmsTemplate(SmsProperties smsProperties) {
        return new Sms4jSmsTemplate(smsProperties);
    }

}
