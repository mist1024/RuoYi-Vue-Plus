package com.ruoyi.gateway.exception;

import com.ruoyi.gateway.constant.ErrorMessageConstant;
import org.springframework.http.HttpStatus;

import java.util.concurrent.TimeUnit;

/**
 * 网关异常-限流异常
 *
 * @author Wenchao Gong
 * @date 2021-10-04
 */
public class RateLimitException extends GatewayException {

    public RateLimitException(long limit, TimeUnit timeUnit)
    {
        super(HttpStatus.TOO_MANY_REQUESTS, String.format(ErrorMessageConstant.RATE_LIMIT, limit, timeUnit.name()));
    }
}
