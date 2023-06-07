package com.ruoyi.rsaencrypt.annotation;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;


/**
 * @author monkey
 * @desc 请求RSA数据解密
 * @date 2018/10/25 20:17
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
@Order(0)
public @interface RsaSecurityParameter {

    /**
     * 入参是否解密，默认不解密
     */
    boolean inDecode() default false;

    /**
     * 出参是否加密，默认加密
     */
    boolean outEncode() default true;


    /**
     * 出参公钥
     * @return
     */
    String outPublicKey() default "" ;



}
