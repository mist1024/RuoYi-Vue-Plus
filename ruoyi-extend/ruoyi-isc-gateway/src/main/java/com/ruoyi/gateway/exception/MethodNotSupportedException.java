package com.ruoyi.gateway.exception;

import com.ruoyi.gateway.constant.ErrorMessageConstant;
import org.springframework.http.HttpMethod;

/**
 * 网关异常-请求方法不支持
 *
 * @author Wenchao Gong
 * @date 2021-10-04
 */
public class MethodNotSupportedException extends GatewayException {

    public MethodNotSupportedException(HttpMethod httpMethod)
    {
        super(String.format(ErrorMessageConstant.METHOD_NOT_SUPPORTED, httpMethod));
    }
}
