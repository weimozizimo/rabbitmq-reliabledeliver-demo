package com.wyf.compent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyf.bo.MsgTxtBo;
import com.wyf.entity.MessageContent;
import com.wyf.enumration.MsgStatusEnum;
import com.wyf.mapper.MsgContentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
*@Description 消息不可达监听
*@Author weiyifei
*@date 2021/6/19
*/
@Component
@Slf4j
public class MsgReturnListener implements RabbitTemplate.ReturnCallback {

    @Autowired
    private MsgContentMapper msgContentMapper;


    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            MsgTxtBo msgTxtBo = objectMapper.readValue(message.getBody(), MsgTxtBo.class);
            log.info("无法路由消息内容：{},cause:{}",msgTxtBo,replyText);

            MessageContent messageContent = new MessageContent();
            messageContent.setErrCause(replyText);
            messageContent.setUpdateTime(new Date());
            messageContent.setMsgStatus(MsgStatusEnum.SENDING_FAIL.getCode());
            messageContent.setMsgId(msgTxtBo.getMsgId());

            //更新消息表
            msgContentMapper.updateMsgStatus(messageContent);
        }catch (Exception e){
            log.error("更新消息表异常:{}",e);
        }
    }
}
