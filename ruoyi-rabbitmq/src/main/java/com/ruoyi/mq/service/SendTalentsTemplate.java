package com.ruoyi.mq.service;

import com.ruoyi.mq.config.TalentsDelayRabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送人才认定延时模板
 */
@Component
public class SendTalentsTemplate {

    @Autowired
    private RabbitTemplate amqpTemplate;

    public void sendDelayTalentsMsgStr(String msg){
//        5*24=120小时=120*60分钟=7200分=7200*60秒=432000秒=432000*1000=432000000毫秒
        Message build = MessageBuilder.withBody(msg.getBytes()).build();
        build.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        build.getMessageProperties().setDelay(259200000);
        amqpTemplate.convertAndSend(TalentsDelayRabbitConfig.TALENTS_PLUGIN_DELAY_EXCHANGE,TalentsDelayRabbitConfig.TALENTS_PLUGIN_DELAY_KEY,build);
    }
}
