package com.ruoyi.framework.config;

import com.ruoyi.framework.config.properties.MailProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * JavaMail 配置
 *
 * @author Michelle.Chung
 */
@Configuration
public class MailConfig {

    @Resource
    private MailProperties mailProperties;

    /**
     * 初始化 JavaMailSender
     */
    @Bean
    @ConditionalOnProperty(value = "spring.mail.enabled", havingValue = "true")
    public JavaMailSenderImpl getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailProperties.getProtocol());
        props.put("mail.smtp.auth", mailProperties.getAuth());
        props.put("mail.smtp.starttls.enable", mailProperties.getStarttlsEnable());
        props.put("mail.smtp.ssl.trust", mailProperties.getSslTrust());
        props.put("mail.debug", mailProperties.getDebug());

        return mailSender;
    }

}
