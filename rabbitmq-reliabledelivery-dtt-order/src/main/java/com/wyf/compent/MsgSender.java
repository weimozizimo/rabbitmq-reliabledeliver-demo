package com.wyf.compent;

import com.alibaba.druid.filter.AutoLoad;
import com.wyf.bo.MsgTxtBo;
import com.wyf.constants.MqConst;
import com.wyf.mapper.MsgContentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
*@Description 消息发送组件
*@Author weiyifei
*@date 2021/6/19
*/
@Component
@Slf4j
public class MsgSender implements InitializingBean {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MsgReturnListener msgReturnListener;

    @Autowired
    private MsgConfirm msgConfirml;


    /**
     * 真正的消息发送方法
     * @param msgTxtBo
     */
    public void sendMsg(MsgTxtBo msgTxtBo){
        log.info("发送的消息id：{}",msgTxtBo.getMsgId());
        CorrelationData correlationData = new CorrelationData(msgTxtBo.getMsgId());

        rabbitTemplate.convertAndSend(MqConst.ORDER_TO_PRODUCT_EXCHANGE_NAME,MqConst.ORDER_TO_PRODUCT_ROUTING_KEY,msgTxtBo,correlationData);
    }

    /**
     * 配置rabbitTemplate
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //设置消息确认监听
        rabbitTemplate.setConfirmCallback(msgConfirml);
        //设置消息不可达监听
        rabbitTemplate.setReturnCallback(msgReturnListener);
        //设置消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
    }
}
