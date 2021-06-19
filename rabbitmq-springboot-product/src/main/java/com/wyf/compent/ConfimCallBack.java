package com.wyf.compent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * 消息确认模式的回调函数
 */
@Slf4j
public class ConfimCallBack implements RabbitTemplate.ConfirmCallback {


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String msgId = correlationData.getId();

        log.info(cause);
        log.info(String.valueOf(ack));

        if(ack){
            log.info(msgId+"发送成功");
        }else {
            log.warn(msgId+"发送失败："+cause);
        }
    }
}
