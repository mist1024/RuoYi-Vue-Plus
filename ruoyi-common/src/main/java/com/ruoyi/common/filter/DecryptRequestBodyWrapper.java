package com.ruoyi.common.filter;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.encrypt.EncryptContext;
import com.ruoyi.common.encrypt.encryptor.AesEncryptor;
import com.ruoyi.common.encrypt.encryptor.RsaEncryptor;
import com.ruoyi.common.enums.AlgorithmType;
import com.ruoyi.common.enums.EncodeType;
import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 解密请求参数工具类
 *
 * @author wdhcr
 */
public class DecryptRequestBodyWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public DecryptRequestBodyWrapper(HttpServletRequest request, RsaEncryptor rsaEncryptor, String headerFlag) throws IOException {
        super(request);
        String requestRsa = request.getHeader(headerFlag);
        if (StringUtils.isEmpty(requestRsa)) {
            throw new BaseException("加密AES的动态密码不能为空");
        }
        String decryptAes = new String(Base64.decode(rsaEncryptor.decrypt(requestRsa)));
        request.setCharacterEncoding(Constants.UTF8);
        byte[] readBytes = IoUtil.readBytes(request.getInputStream(), false);
        String requestBody = StringUtils.toEncodedString(readBytes, StandardCharsets.UTF_8);
        EncryptContext encryptContext = new EncryptContext();
        encryptContext.setAlgorithm(AlgorithmType.AES);
        encryptContext.setPassword(decryptAes);
        encryptContext.setEncode(EncodeType.BASE64);
        AesEncryptor aesEncryptor = new AesEncryptor(encryptContext);
        String decryptBody = aesEncryptor.decrypt(requestBody);
        body = decryptBody.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }


    @Override
    public int getContentLength() {
        return body.length;
    }

    @Override
    public long getContentLengthLong() {
        return body.length;
    }

    @Override
    public String getContentType() {
        return MediaType.APPLICATION_JSON_VALUE;
    }



    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() {
                return bais.read();
            }

            @Override
            public int available() {
                return body.length;
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }
}
