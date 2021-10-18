package com.ruoyi.gateway.exception;

import com.ruoyi.gateway.constant.ErrorMessageConstant;

/**
 * 网关异常-规则已过期
 *
 * @author Wenchao Gong
 * @date 2021-10-04
 */
public class RuleExpiredException extends GatewayException {

    public RuleExpiredException()
    {
        super(ErrorMessageConstant.RULE_EXPIRED);
    }
}
