package com.ruoyi.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PluginDelayRabbitConfig {

    public static final String PLUGIN_DELAY_EXCHANGE =  "pluginDelayExchange";
    public static final String PLUGIN_DELAY_QUEUE =  "pluginDelayQueue";
    public static final String PLUGIN_DELAY_KEY_TALENTS =  "pluginDelayKeyTalents";

    @Bean(PLUGIN_DELAY_EXCHANGE)
    public CustomExchange pluginDelayExchange() {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("x-delayed-type", "direct");//必须要配置这个类型，可以是direct,topic和fanout
        //第二个参数必须为x-delayed-message
        return new CustomExchange(PLUGIN_DELAY_EXCHANGE,"x-delayed-message",true, false, argMap);
    }

    @Bean(PLUGIN_DELAY_QUEUE)
    public Queue pluginDelayQueue(){
        return new Queue(PLUGIN_DELAY_QUEUE,true,false,false);
    }

    //使用下 ImmediateRequeueMessageRecoverer 重新排队在RabbitMQConfiguration中配置
    /*@Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate,"test_dead_letter_exchange","test_dead_letter_key");
    }*/

    @Bean("pluginDelayBinding")
    public Binding pluginDelayBinding(@Qualifier(PLUGIN_DELAY_QUEUE) Queue queue, @Qualifier(PLUGIN_DELAY_EXCHANGE) CustomExchange customExchange){
        return BindingBuilder.bind(queue).to(customExchange).with(PLUGIN_DELAY_KEY_TALENTS).noargs();
    }
}
