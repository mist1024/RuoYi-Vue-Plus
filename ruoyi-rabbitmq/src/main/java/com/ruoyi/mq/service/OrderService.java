package com.ruoyi.mq.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author whc
 * @date 2022/5/23 18:50
 */

@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void makeOrder() {
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功：" + orderId);
        String exchange_name = "fanout_order_exchange";
        String routeingKey = "";
        rabbitTemplate.convertAndSend(exchange_name, routeingKey, orderId);
    }
}
