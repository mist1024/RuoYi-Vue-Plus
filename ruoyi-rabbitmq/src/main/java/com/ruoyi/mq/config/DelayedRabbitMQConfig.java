/*
package com.ruoyi.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedRabbitMQConfig {
    */
/**
     * 延迟队列名称
     *//*

    public static final String DELAYED_QUEUE_NAME = "delay.queue.demo.delay.queue";
    */
/**
     * 延迟队列交换机
     *//*

    public static final String DELAYED_EXCHANGE_NAME = "delay.queue.demo.delay.exchange";
    */
/**
     * 延迟队列路由键
     *//*

    public static final String DELAYED_ROUTING_KEY = "delay.queue.demo.delay.routingkey";

    // 声明延迟队列
    @Bean
    public Queue delayedSmsQueueInit() {
        return new Queue("sms_delayed_queue",true);
    }

    // 声明延迟交换机
    @Bean
    public CustomExchange delayedExchangeInit() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange("delayed_exchange", "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding delayedBindingSmsQueue(Queue delayedSmsQueueInit, CustomExchange customExchange) {
        // 延迟队列绑定延迟交换机并设置RoutingKey为sms
        return BindingBuilder.bind(delayedSmsQueueInit).to(customExchange).with("sms").noargs();
    }
}
*/
