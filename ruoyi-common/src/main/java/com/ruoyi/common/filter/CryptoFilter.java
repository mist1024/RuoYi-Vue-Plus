package com.ruoyi.common.filter;

import com.ruoyi.common.encrypt.EncryptContext;
import com.ruoyi.common.encrypt.encryptor.RsaEncryptor;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Crypto 过滤器
 *
 * @author wdhcr
 */
public class CryptoFilter implements Filter {

    public static final String CRYPTO_ENABLE = "enable";
    public static final String CRYPTO_PUBLIC_KEY = "publicKey";
    public static final String CRYPTO_PRIVATE_KEY = "privateKey";
    public static final String CRYPTO_HEADER_FLAG = "headerFlag";
    private RsaEncryptor rsaEncryptor;
    private boolean enable;
    private String headerFlag;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String enableStr = filterConfig.getInitParameter(CryptoFilter.CRYPTO_ENABLE);
        enable = Boolean.parseBoolean(enableStr);
        EncryptContext encryptContext = new EncryptContext();
        encryptContext.setAlgorithm(AlgorithmType.RSA);
        encryptContext.setPublicKey(filterConfig.getInitParameter(CryptoFilter.CRYPTO_PUBLIC_KEY));
        encryptContext.setPrivateKey(filterConfig.getInitParameter(CryptoFilter.CRYPTO_PRIVATE_KEY));
        headerFlag = filterConfig.getInitParameter(CryptoFilter.CRYPTO_HEADER_FLAG);
        rsaEncryptor = new RsaEncryptor(encryptContext);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (enable && request instanceof HttpServletRequest
            && StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            requestWrapper = new DecryptRequestBodyWrapper((HttpServletRequest) request, rsaEncryptor, headerFlag);
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
