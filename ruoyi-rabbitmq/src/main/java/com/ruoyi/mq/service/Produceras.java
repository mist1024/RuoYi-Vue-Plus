package com.ruoyi.mq.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class Produceras {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendDelayMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend("delayed_exchange", "sms", msg, a -> {
            a.getMessageProperties().setDelay(delayTime*1000);
            return a;
        });
    }
}

