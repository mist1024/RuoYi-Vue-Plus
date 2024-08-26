package org.dromara.common.web.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 谷歌验证配置
 *
 * @author knight
 */
@Configuration(proxyBeanMethods = false)
public class GoogleAuthConfig {

    @Bean
    public GoogleAuthenticator googleAuthenticator() {
        return new GoogleAuthenticator();
    }

}
