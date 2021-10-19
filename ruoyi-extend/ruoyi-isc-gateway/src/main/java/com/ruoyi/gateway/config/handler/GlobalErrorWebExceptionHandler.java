package com.ruoyi.gateway.config.handler;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 网关异常通用处理器，只作用在webflux 环境下 , 优先级低于 {@link ResponseStatusExceptionHandler} 执行
 * @author Wenchao Gong
 * @date 2021-10-18
 */
@Slf4j
@Order(-1)
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex)
    {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // header set
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            final ResponseStatusException statusException = (ResponseStatusException) ex;
            response.setStatusCode(statusException.getStatus());
        }
        Optional<Integer> code = Optional.ofNullable(response.getRawStatusCode());
        final String message = getMessage(ex);

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSONUtil.toJsonStr(result(code, message))
                    .getBytes(StandardCharsets.UTF_8));
        }));
    }

    private String getMessage(Throwable ex)
    {
        if(ex instanceof ResponseStatusException) {
            String reason = ((ResponseStatusException) ex).getReason();
            if(log.isDebugEnabled()){
                log.debug(ex.getMessage());
            }
            if(StrUtil.isNotBlank(reason)) {
                return reason;
            }
        }
        String message = ex.getMessage();
        log.error(message, ex);
        return message;
    }

    private Map<String, Object> result(Optional<Integer> code, String message) {
        final HashMap<String, Object> result = MapUtil.newHashMap(3);
        result.put("code", code.orElse(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        result.put("message", message);
        result.put("data", null);
        return result;
    }
}
