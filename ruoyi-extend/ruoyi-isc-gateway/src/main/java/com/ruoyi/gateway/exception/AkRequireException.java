package com.ruoyi.gateway.exception;

import com.ruoyi.gateway.constant.ErrorMessageConstant;

/**
 * 网关异常-AK必要
 *
 * @author Wenchao Gong
 * @date 2021-10-04
 */
public class AkRequireException extends GatewayException {

    public AkRequireException(String ak)
    {
        super(String.format(ErrorMessageConstant.AK_REQUIRE, ak));
    }
}
