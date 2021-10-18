package com.ruoyi.gateway.exception;

import com.ruoyi.gateway.constant.ErrorMessageConstant;

/**
 * 网关异常-请求方法不支持
 *
 * @author Wenchao Gong
 * @date 2021-10-04
 */
public class ContentTypeNotSupportedException extends GatewayException {

    public ContentTypeNotSupportedException()
    {
        super(ErrorMessageConstant.CONTENT_TYPE_NOT_SUPPORTED);
    }
}
