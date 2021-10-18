package com.ruoyi.gateway.exception;

import cn.hutool.core.util.StrUtil;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * 网关异常-限流异常
 *
 * @author Wenchao Gong
 * @date 2021-10-04
 */
@NoArgsConstructor
public class RateLimitException extends GatewayException {

    private TimeUnit timeUnit;
    private int replenishRate;
    private int tokensLeft;
    private String message;
    public RateLimitException(int replenishRate, int tokensLeft, TimeUnit timeUnit) {
        this.replenishRate = replenishRate;
        this.tokensLeft = tokensLeft;
        this.timeUnit = timeUnit;
        String unit = StrUtil.upperFirst(timeUnit.name().toLowerCase());
        message = String.format("服务限流: 上限[%d/%s], 实际[%d/%s]", replenishRate, unit, tokensLeft, unit);
    }
}
