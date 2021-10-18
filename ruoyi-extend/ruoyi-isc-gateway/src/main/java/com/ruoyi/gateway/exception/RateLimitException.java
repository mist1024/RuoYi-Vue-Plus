package com.ruoyi.gateway.exception;

import com.ruoyi.gateway.constant.ErrorMessageConstant;
import org.springframework.http.HttpStatus;

/**
 * 网关异常-限流异常
 *
 * @author Wenchao Gong
 * @date 2021-10-04
 */
public class RateLimitException extends GatewayException {

    public RateLimitException()
    {
        super(HttpStatus.TOO_MANY_REQUESTS, ErrorMessageConstant.RATE_LIMIT);
    }
}
