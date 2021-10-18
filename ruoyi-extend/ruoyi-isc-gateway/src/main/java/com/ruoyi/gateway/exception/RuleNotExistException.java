package com.ruoyi.gateway.exception;

import com.ruoyi.gateway.constant.ErrorMessageConstant;

/**
 * 网关异常-规则不存在
 *
 * @author Wenchao Gong
 * @date 2021-10-04
 */
public class RuleNotExistException extends GatewayException {

    public RuleNotExistException()
    {
        super(ErrorMessageConstant.RULE_NOT_EXIST);
    }
}
