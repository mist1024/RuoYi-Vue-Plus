package com.ruoyi.common.advice;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.Platform;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.ruoyi.common.core.domain.RsaSecurity;
import com.ruoyi.common.core.service.ConfigService;
import com.ruoyi.common.core.service.IRsaSecurityService2;
import com.ruoyi.common.utils.RSAUtil;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.rmi.ServerException;
import java.util.List;

/**
 * @author monkey
 * @desc 请求数据解密
 * @date 2018/10/29 20:17
 */
@ControllerAdvice
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(DecodeRequestBodyAdvice.class);
    private static final IRsaSecurityService2 rsaSecurityService2= SpringUtils.getBean(IRsaSecurityService2.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        try {
            String path = inputMessage.getHeaders().getFirst("path");
            String method = inputMessage.getHeaders().getFirst("method");
            if (ObjectUtil.isNotNull(path)) {
                RsaSecurity info = rsaSecurityService2.getInfo(path, StringUtils.toRootUpperCase(method));
                if (ObjectUtil.isNotNull(info)) {
                    if ("1".equals(info.getRestricted())){
                        throw new ServerException("接口已限制请求");
                    }
                    if ("1".equals(info.getInDecode())) {
                        return new RsaHttpInputMessage(inputMessage, info.getPrivateKey());
                    }

                }
            }
            return inputMessage;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密出现异常：" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    class RsaHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public RsaHttpInputMessage(HttpInputMessage inputMessage,String privateKey ) throws Exception {
            this.headers = inputMessage.getHeaders();
            String targe="pc";
            List<String> list = headers.get("targe");
            if (ObjectUtil.isNotNull(list) && list.size()>0) {
                boolean wxixin = list.contains("weixin");
                if (wxixin) {
                    targe = "weixin";
                }
            }
            this.body = IoUtil.toUtf8Stream(easpString(IoUtil.readUtf8(inputMessage.getBody()),targe,privateKey));
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        public String easpString(String requestData,String targe,String privateKey) throws IOException {
            if (requestData != null && !requestData.equals("")) {
                if (StringUtils.isEmpty(requestData)) {
                    throw new RuntimeException("参数【requestData】缺失异常！");
                } else {
                    String content = null;
                    try {
                        logger.info("解密密文:"+requestData);
                        String substring1 = StringUtils.substring(requestData, 0, 1);
                        if ("\"".equals(substring1)){
                            String data1 = requestData.substring(1);
                            String substring = requestData.substring(1, data1.length());
                            content = RSAUtil.privateKeyDecryptStr(substring,privateKey);
                        }else {
                            content = RSAUtil.privateKeyDecryptStr(requestData,privateKey);
                        }
                        logger.info("解密明文:"+content);
                    } catch (Exception e) {
                        throw new RuntimeException("msg:参数【aseKey】解析异常！");
                    }
                    try {
                    } catch (Exception e) {
                        throw new ServerException("msg:参数【content】解析异常！");
                    }
                    if (StringUtils.isEmpty(content)) {
                        throw new RuntimeException("msg:参数【requestData】解析参数空指针异常!");
                    }
                    return content;
                }
            }
            throw new RuntimeException("msg:参数【requestData】不合法异常！");
        }
    }
}
