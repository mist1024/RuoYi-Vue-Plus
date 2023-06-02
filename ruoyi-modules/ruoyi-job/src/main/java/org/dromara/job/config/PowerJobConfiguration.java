package org.dromara.job.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import tech.powerjob.worker.PowerJobWorker;

/**
 * 启动定时任务
 * @author yhan219
 * @since 2023/6/2
 */
@Configuration
@ConditionalOnBean(PowerJobWorker.class)
@EnableScheduling
public class PowerJobConfiguration {


}
