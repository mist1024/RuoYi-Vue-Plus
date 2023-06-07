package com.ruoyi.mq.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author whc
 * @date 2022/5/23 18:53
 */
@RabbitListener(queues = "email.fanout.queue")
@Component
public class FanoutEmailConsumer {

    @RabbitHandler
    public void messageService(String message) {
        System.out.println("fanout email ==>" + message);
    }
}
