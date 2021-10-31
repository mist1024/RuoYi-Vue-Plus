package com.ruoyi.gateway.exception;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.gateway.constant.ErrorMessageConstant;

import java.util.Date;

/**
 * 网关异常-规则已过期
 *
 * @author Wenchao Gong
 * @date 2021-10-04
 */
public class RuleExpiredException extends GatewayException {

    public RuleExpiredException(Date endTime)
    {
        super(String.format(ErrorMessageConstant.RULE_EXPIRED, DateUtil.formatDateTime(endTime)));
    }
}
