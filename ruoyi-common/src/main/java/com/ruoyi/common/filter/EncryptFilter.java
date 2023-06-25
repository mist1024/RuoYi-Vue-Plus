package com.ruoyi.common.filter;

import cn.hutool.core.io.IoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.encrypt.EncryptContext;
import com.ruoyi.common.encrypt.encryptor.AesEncryptor;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.enums.EncodeType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 加解密
 *
 * @author ltyzzz
 * @email ltyzzz2000@gmail.com
 * @date 2023/4/27 01:15
 */
public class EncryptFilter implements Filter {

    private String secret;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        secret = filterConfig.getInitParameter("secret");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        System.out.println("加解密过滤器，" + uri);

        EncryptContext encryptContext = new EncryptContext();
        encryptContext.setPassword(secret);
        encryptContext.setAlgorithm(AlgorithmType.AES);
        encryptContext.setEncode(EncodeType.BASE64);
        AesEncryptor aesEncryptor = new AesEncryptor(encryptContext);

        byte[] body;
        if (req.getContentLength() > 0) {
            String reqBody = getRequestBody(req);
            ObjectMapper objectMapper = new ObjectMapper();
            Map map = objectMapper.readValue(reqBody, Map.class);
            String encryptData = (String) map.get("encryptData");
            String data = aesEncryptor.decrypt(encryptData);
            System.out.println(data);
            body = data.getBytes();
        } else {
            body = IoUtil.readBytes(request.getInputStream(), false);
        }

        EncryptRequestWrapper reqWrapper = new EncryptRequestWrapper(req, body);
        EncryptResponseWrapper respWrapper = new EncryptResponseWrapper(resp);
        chain.doFilter(reqWrapper, respWrapper);
        byte[] resData = respWrapper.getResponseData();
        String encrypt = aesEncryptor.encrypt(new String(resData), EncodeType.BASE64);
        PrintWriter out = response.getWriter();
        out.print(encrypt);
        out.flush();
        out.close();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private String getRequestBody(ServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
