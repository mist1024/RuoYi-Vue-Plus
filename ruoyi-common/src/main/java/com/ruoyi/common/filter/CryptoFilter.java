package com.ruoyi.common.filter;

import com.ruoyi.common.encrypt.EncryptContext;
import com.ruoyi.common.encrypt.encryptor.RsaEncryptor;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.utils.StringUtils;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Crypto 过滤器
 *
 * @author wdhcr
 */
public class CryptoFilter implements Filter {

    public static final String CRYPTO_PUBLIC_KEY = "publicKey";
    public static final String CRYPTO_PRIVATE_KEY = "privateKey";
    public static final String CRYPTO_HEADER_FLAG = "headerFlag";
    private RsaEncryptor rsaEncryptor;
    private String headerFlag;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        EncryptContext encryptContext = new EncryptContext();
        encryptContext.setAlgorithm(AlgorithmType.RSA);
        encryptContext.setPublicKey(filterConfig.getInitParameter(CryptoFilter.CRYPTO_PUBLIC_KEY));
        encryptContext.setPrivateKey(filterConfig.getInitParameter(CryptoFilter.CRYPTO_PRIVATE_KEY));
        headerFlag = filterConfig.getInitParameter(CryptoFilter.CRYPTO_HEADER_FLAG);
        rsaEncryptor = new RsaEncryptor(encryptContext);
    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        ServletRequest requestWrapper = null;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)
            && (HttpMethod.PUT.matches(httpServletRequest.getMethod()) || HttpMethod.POST.matches(httpServletRequest.getMethod()))) {
            requestWrapper = new DecryptRequestBodyWrapper(httpServletRequest, rsaEncryptor, headerFlag);
        }
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }
}
