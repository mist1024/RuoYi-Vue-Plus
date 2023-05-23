package com.ruoyi.oss.config;

import com.ruoyi.oss.constant.OssConstant;
import com.ruoyi.oss.properties.OssProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * OSS资源配置
 *
 * @author CYC
 * @since 2023-05-23
 */
@Configuration
public class OssResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(OssConstant.LOCAL_RESOURCE_PATH + "/**").addResourceLocations("file:///" + OssProperties.getLocalResourceDir());
    }
}
