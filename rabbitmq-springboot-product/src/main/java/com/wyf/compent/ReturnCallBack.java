package com.wyf.compent;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息不可达监听
 */
@Slf4j
public class ReturnCallBack implements RabbitTemplate.ReturnCallback {


    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.warn("correaletionId:{}",message.getMessageProperties().getCorrelationId());
        log.warn("replyText:{}",replyText);
        log.warn("replyCode:{}",replyCode);
        log.warn("rountingKey:{}",exchange);
        log.info("需要更新数据库日志表的消息记录为不可达");
    }
}
