package com.ruoyi.gateway.exception;

import com.ruoyi.gateway.constant.ErrorMessageConstant;
import org.springframework.http.MediaType;

/**
 * 网关异常-请求方法不支持
 *
 * @author Wenchao Gong
 * @date 2021-10-04
 */
public class ContentTypeNotSupportedException extends GatewayException {

    public ContentTypeNotSupportedException(MediaType contentType)
    {
        super(String.format(ErrorMessageConstant.CONTENT_TYPE_NOT_SUPPORTED, contentType));
    }
}
