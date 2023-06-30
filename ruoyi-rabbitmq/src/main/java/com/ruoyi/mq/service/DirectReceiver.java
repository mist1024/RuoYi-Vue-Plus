package com.ruoyi.mq.service;

import com.rabbitmq.client.Channel;
import com.ruoyi.mq.config.TalentsDelayRabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DirectReceiver {

    @RabbitListener(queues = TalentsDelayRabbitConfig.TALENTS_PLUGIN_DELAY_QUEUE)//监听的队列名称 TestDirectQueue
    public void processs(Message message,Channel channel) throws IOException {
        String msg = new String(message.getBody(), "UTF-8");
        System.out.println("msg = " + msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        // 重新发送消息到队尾
        // 参数：Exchange、routingKey、额外的设置属性、消息字节数组
        /*channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
            message.getMessageProperties().getReceivedRoutingKey(),
            null,msg.getBytes());*/
    }

}
