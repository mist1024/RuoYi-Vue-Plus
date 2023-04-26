package com.ruoyi.rsaencrypt.advice;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.rsaencrypt.annotation.RsaSecurityParameter;
import com.ruoyi.rsaencrypt.utls.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author monkey
 * @desc 返回数据加密
 * @date 2018/10/25 20:17
 */
@ControllerAdvice
@RsaSecurityParameter()
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice {
    private final static Logger logger = LoggerFactory.getLogger(EncodeResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 此处要用反射将字段中的注解解析出来
        if (methodParameter.getMethod().isAnnotationPresent(RsaSecurityParameter.class)) {
            //获取注解配置的包含和去除字段
            RsaSecurityParameter serializedField = methodParameter.getMethodAnnotation(RsaSecurityParameter.class);
            //出参是否需要加密
            if (serializedField.outEncode()) {
                return encodeRsa(methodParameter, body);
            }
        }
        JSONObject jsonObject = JSONUtil.parseObj(body);
        Object code = jsonObject.get("code");
        String num = "500";
        String num2 ="401";
        String num3 ="403";
        if (ObjectUtil.isNotNull(code)){
            if (num.equals(code.toString()) || num2.equals(code.toString()) || num3.equals(code.toString())){
                return encodeRsa(methodParameter, body);
            }
        }
        return  body;
    }

    /**
     * RSA私钥加密
     *
     * @param methodParameter
     * @param body
     * @return
     */
    private Object encodeRsa(MethodParameter methodParameter, Object body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            logger.info("对返回数据 :【" + result + "】进行加密");
            String s = RSAUtil.publicKeyEncryptBase64(result);
            logger.info("加密之后密文:"+s);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行加密出现异常：" + e.getMessage());
        }
        return body;
    }
}



