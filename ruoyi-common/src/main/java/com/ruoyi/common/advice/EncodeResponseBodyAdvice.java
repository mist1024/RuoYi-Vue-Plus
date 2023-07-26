package com.ruoyi.common.advice;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RsaSecurity;
import com.ruoyi.common.core.service.IRsaSecurityService2;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.common.utils.RSAUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author monkey
 * @desc 返回数据加密
 * @date 2018/10/25 20:17
 */
@ControllerAdvice
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice {
    private final static Logger logger = LoggerFactory.getLogger(EncodeResponseBodyAdvice.class);
    private static final IRsaSecurityService2 rsaSecurityService=SpringUtils.getBean(IRsaSecurityService2.class);


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 此处要用反射将字段中的注解解析出来
        String method = serverHttpRequest.getMethod().name();
        String path = serverHttpRequest.getURI().getPath();
        RsaSecurity info = rsaSecurityService.getInfo(path,method);
        if (ObjectUtil.isNotNull(info)) {
            if ("1".equals(info.getRestricted())){
                return R.fail("接口已限制请求");
            }
            if ("1".equals(info.getOutEncode())) {
                serverHttpResponse.getHeaders().add("isRsaencrypt","true");
                return encodeRsa(methodParameter, body, info.getPublicKey());
            }
        }
        serverHttpResponse.getHeaders().add("isRsaencrypt","false");
        return body;
        /*if (methodParameter.getMethod().isAnnotationPresent(RsaSecurityParameter.class)) {
            //获取注解配置的包含和去除字段
            RsaSecurityParameter serializedField = methodParameter.getMethodAnnotation(RsaSecurityParameter.class);
            //出参是否需要加密
            if (serializedField.outEncode()) {
                return encodeRsa(methodParameter, body,serializedField.outPublicKey());
            }
        }
        JSONObject jsonObject = JSONUtil.parseObj(body);
        Object code = jsonObject.get("code");
        String num = "500";
        String num2 ="401";
        String num3 ="403";
        if (ObjectUtil.isNotNull(code)){
            if (num.equals(code.toString()) || num2.equals(code.toString()) || num3.equals(code.toString())){
                return encodeRsa(methodParameter, body,"");
            }
        }
        return  body;*/
    }

    /**
     * RSA私钥加密
     *
     * @param methodParameter
     * @param body
     * @return
     */
    private Object encodeRsa(MethodParameter methodParameter, Object body,String outPublicKey) {
        try {
            String result = JsonUtils.toJsonString(body);
            logger.info("对返回数据 :【" + result + "】进行加密");
            String s = RSAUtil.publicKeyEncryptBase64(result,outPublicKey);
            logger.info("加密之后密文:"+s);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行加密出现异常：" + e.getMessage());
        }
        return body;
    }
}



