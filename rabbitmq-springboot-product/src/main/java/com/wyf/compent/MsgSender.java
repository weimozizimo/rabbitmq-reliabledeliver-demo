package com.wyf.compent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyf.entity.Order;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * 消息发送组件
 */
@Component
public class MsgSender {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final static String exchange = "springboot.direct.exchange";
    private final static String delayExchange = "delayExchange";
    private final static String routingkey1 = "springboot.key";

    /**
     * 测试基础信息发送
     * @param msg
     * @param msgProp
     */
    public void sendMsg(String msg, Map<String,Object> msgProp){

        MessageProperties messageProperties = new MessageProperties();

        messageProperties.getHeaders().putAll(msgProp);

        //构件消息对象
        Message message = new Message(msg.getBytes(), messageProperties);

        //构件correlationData 用于做可靠性投递，ID:必须是全局唯一的
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(exchange,routingkey1,message,correlationData);
    }


    /**
     * 发送对象
     * @param order
     */
    public void sendMsg(Order order) throws JsonProcessingException {

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = objectMapper.writeValueAsString(order);
        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message(orderJson.getBytes(), messageProperties);


//        //json序列化后发送
//        rabbitTemplate.convertAndSend(exchange,routingkey1,message,correlationData);

        //直接发送
        rabbitTemplate.convertAndSend(exchange,routingkey1,order,correlationData);

    }

    /**
     * 延时发送
     * @param order
     */
    public void sendDelayMsg(Order order){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(delayExchange, routingkey1, order, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay",10000);//设置延时时间
                return message;
            }
        },correlationData);

    }

}
