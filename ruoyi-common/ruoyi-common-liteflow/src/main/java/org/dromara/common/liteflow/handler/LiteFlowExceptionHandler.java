package org.dromara.common.liteflow.handler;

import cn.hutool.http.HttpStatus;
import com.yomahub.liteflow.exception.LiteFlowException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * LiteFlow 异常处理器
 *
 * @author AprilWind
 */
@Slf4j
@RestControllerAdvice
public class LiteFlowExceptionHandler {

    /**
     * 规则引擎内部逻辑发生错误抛出的异常
     */
    @ExceptionHandler(LiteFlowException.class)
    public R<Void> handleLockFailureException(LiteFlowException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("处理请求'{}'时发生LiteFlow异常: ", requestURI, e);
        return R.fail(HttpStatus.HTTP_INTERNAL_ERROR, "系统内部错误，请稍后再试...");
    }

}
