package org.dromara.common.encrypt.filter;

import cn.hutool.core.util.ObjectUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.encrypt.properties.ApiDecryptProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.io.IOException;


/**
 * Crypto 过滤器
 *
 * @author wdhcr
 */
public class CryptoFilter implements Filter {
    private final ApiDecryptProperties properties;

    public CryptoFilter(ApiDecryptProperties properties) {
        this.properties = properties;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean encryptFlag = properties.getResponseEnabled();
        ServletRequest requestWrapper = null;
        ServletResponse responseWrapper = null;
        EncryptResponseBodyWrapper responseBodyWrapper = null;
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        // 是否为 json 请求
        if (StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            // 是否为 put 或者 post 请求
            if (HttpMethod.PUT.matches(servletRequest.getMethod()) || HttpMethod.POST.matches(servletRequest.getMethod())) {
                // 是否存在加密标头
                String headerValue = servletRequest.getHeader(properties.getHeaderFlag());
                if (StringUtils.isNotBlank(headerValue)) {
                    requestWrapper = new DecryptRequestBodyWrapper(servletRequest, properties.getPublicKey(), properties.getPrivateKey(), properties.getHeaderFlag());
                }
                // 判断是否响应加密
                if (encryptFlag) {
                    responseBodyWrapper = new EncryptResponseBodyWrapper(servletResponse);
                    responseWrapper = responseBodyWrapper;
                }
            }
        }

        chain.doFilter(
            ObjectUtil.defaultIfNull(requestWrapper, request),
            ObjectUtil.defaultIfNull(responseWrapper, response));

        if (encryptFlag) {
            servletResponse.reset();
            // 对原始内容加密
            String encryptContent = responseBodyWrapper.getEncryptContent(
                servletResponse, properties.getResponsePublicKey(), properties.getHeaderFlag());
            // 对加密后的内容写出
            servletResponse.getWriter().write(encryptContent);
        }
    }

    @Override
    public void destroy() {
    }
}
