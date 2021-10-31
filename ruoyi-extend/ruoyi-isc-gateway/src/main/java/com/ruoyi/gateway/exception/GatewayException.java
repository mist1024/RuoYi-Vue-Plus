package com.ruoyi.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * 网关异常
 *
 * @author Wenchao Gong
 * @date 2021-10-04
 */
public class GatewayException extends ResponseStatusException {

    public GatewayException(String reason)
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public GatewayException(HttpStatus status)
    {
        super(status);
    }

    public GatewayException(HttpStatus status, String reason)
    {
        super(status, reason);
    }

    public GatewayException(HttpStatus status, String reason, Throwable cause)
    {
        super(status, reason, cause);
    }

    public GatewayException(int rawStatusCode, String reason, Throwable cause)
    {
        super(rawStatusCode, reason, cause);
    }
}
