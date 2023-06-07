package com.ruoyi.mq.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PluginDelayConsumer {

    @RabbitHandler
    @RabbitListener(queues = "PLUGIN_DELAY_QUEUE")//监听延时队列
    public void fanoutConsumer(Message msg, Channel channel) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("【插件延迟队列】【" + sdf.format(new Date()) + "】收到消息：" + msg);
//        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }
}
