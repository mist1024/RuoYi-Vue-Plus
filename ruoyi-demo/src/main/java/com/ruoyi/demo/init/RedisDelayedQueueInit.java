package com.ruoyi.demo.init;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * redis 延时队列初始化
 */
@Component
@Slf4j
public class RedisDelayedQueueInit implements ApplicationContextAware {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 获取应用上下文并获取相应的接口实现类
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RedisDelayQueueHandle> map = applicationContext.getBeansOfType(RedisDelayQueueHandle.class);
        for (Map.Entry<String, RedisDelayQueueHandle> taskEventListenerEntry : map.entrySet()) {
            String listenerName = taskEventListenerEntry.getValue().getClass().getName();
            startThread(listenerName, taskEventListenerEntry.getValue());
        }
    }

    /**
     * 启动线程获取队列
     * @param queueName 队列名称
     * @param redisDelayedQueueListener 任务回调监听
     */
    private <T> void startThread(String queueName, RedisDelayQueueHandle redisDelayedQueueListener) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        //由于此线程需要常驻，可以新建线程，不用交给线程池管理
        Thread thread = new Thread(() -> {
            log.info("启动监听队列线程" + queueName);
            while (true) {
                try {
                    T t = blockingFairQueue.take();
                    log.info("监听队列线程{},获取到值:{}", queueName, t);
                    redisDelayedQueueListener.execute(t);
                } catch (Exception e) {
                    log.info("监听队列线程错误,", e);
                }
            }
        });
        thread.setName(queueName);
        thread.start();
        log.info("(Redis延迟队列启动成功)");
    }
}
