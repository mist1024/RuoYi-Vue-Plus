package org.dromara.common.liteflow.config;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.liteflow.handler.LiteFlowExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * LiteFlow 配置
 *
 * @author AprilWind
 */
@Slf4j
@AutoConfiguration
public class LiteFlowConfig {

    /**
     * 异常处理器
     */
    @Bean
    public LiteFlowExceptionHandler liteFlowExceptionHandler() {
        return new LiteFlowExceptionHandler();
    }

}
