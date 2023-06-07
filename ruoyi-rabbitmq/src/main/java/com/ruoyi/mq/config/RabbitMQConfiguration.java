package com.ruoyi.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author whc
 * @date 2022/5/23 10:18
 */
@Configuration
public class RabbitMQConfiguration {

    //1.声明注册fanout模式的交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_order_exchange", true, false);
    }

    //2.声明队列，sms.fanout.queue email.fanout.queue msg.fanout.queue
    @Bean
    public Queue smsQueue() {
        return new Queue("sms.fanout.queue", true);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("email.fanout.queue", true);
    }

    @Bean
    public Queue msgQueue() {
        return new Queue("msg.fanout.queue", true);
    }

    //3.完成绑定关系（队列与交换机完成绑定关系）
    @Bean
    public Binding smsBind() {
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding emailBind() {
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding msgBind() {
        return BindingBuilder.bind(msgQueue()).to(fanoutExchange());
    }
}
