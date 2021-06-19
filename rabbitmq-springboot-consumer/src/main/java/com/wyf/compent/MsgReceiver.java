package com.wyf.compent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.wyf.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@Slf4j
public class MsgReceiver {


    @RabbitListener(queues = {"bootQueue"})
    public void consumerMsg(Message message, Channel channel) throws IOException {
        System.out.println(Thread.currentThread().getName()+"接收到来自bootqueue：");
        log.info("监听bootqueue的消费信息====:{}",new String(message.getBody()));
        //手工签收
        Long deliveryTag = message.getMessageProperties().getDeliveryTag();

//        channel.basicAck(deliveryTag,false);
        channel.basicNack(deliveryTag,false,false);
    }


    @RabbitListener(queues = {"delayQueue"})
    public void consumerDelayMsg(Message message, Channel channel) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(message.getBody(), Order.class);

        log.info("在{}时收到{}信息",sdf.format(new Date()),order);

        //手工签收
        Long deliveryTag = message.getMessageProperties().getDeliveryTag();

        channel.basicNack(deliveryTag,false,false);
    }

}
