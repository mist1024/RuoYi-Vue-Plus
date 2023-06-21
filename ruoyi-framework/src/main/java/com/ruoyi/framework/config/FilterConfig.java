package com.ruoyi.framework.config;

import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.common.filter.CryptoFilter;
import com.ruoyi.common.filter.RepeatableFilter;
import com.ruoyi.common.filter.XssFilter;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.properties.RequestEncryptProperties;
import com.ruoyi.framework.config.properties.XssProperties;
import com.ruoyi.framework.handler.DecryptUrlHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Filter配置
 *
 * @author Lion Li
 */
@Configuration
public class FilterConfig {

    @Autowired
    private XssProperties xssProperties;

    @Autowired
    private RequestEncryptProperties requestEncryptProperties;

    @Autowired
    private DecryptUrlHandler decryptUrlHandler;

    @Bean
    public FilterRegistrationBean<CryptoFilter> cryptoFilterRegistration() {
        FilterRegistrationBean<CryptoFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new CryptoFilter());
        List<String> urls = decryptUrlHandler.getUrls();
        if (CollectionUtil.isNotEmpty(urls) || requestEncryptProperties.getEnable()) {
            registration.setEnabled(true);
            registration.addUrlPatterns(urls.toArray(new String[0]));
        } else {
            registration.setEnabled(false);
        }
        registration.setName("cryptoFilter");
        HashMap<String, String> param = new HashMap<>();
        param.put(CryptoFilter.CRYPTO_PUBLIC_KEY, requestEncryptProperties.getPublicKey());
        param.put(CryptoFilter.CRYPTO_PRIVATE_KEY, requestEncryptProperties.getPrivateKey());
        param.put(CryptoFilter.CRYPTO_HEADER_FLAG, requestEncryptProperties.getHeaderFlag());
        registration.setInitParameters(param);
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        return registration;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    @ConditionalOnProperty(value = "xss.enabled", havingValue = "true")
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(StringUtils.split(xssProperties.getUrlPatterns(), StringUtils.SEPARATOR));
        registration.setName("xssFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE + 1);
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", xssProperties.getExcludes());
        registration.setInitParameters(initParameters);
        return registration;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

}
