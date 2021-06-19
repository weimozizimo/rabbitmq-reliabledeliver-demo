package com.wyf.compent;

import com.wyf.entity.MessageContent;
import com.wyf.enumration.MsgStatusEnum;
import com.wyf.mapper.MsgContentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
*@Description 消息确认组件
*@Author weiyifei
*@date 2021/6/19
*/
@Component
@Slf4j
public class MsgConfirm implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private MsgContentMapper msgContentMapper;


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String msgId = correlationData.getId();

        if(ack){
            log.info("消息id：{}对象消息签收成功",msgId);
            updateMsgStatusWithAck(msgId);
        }else{
            log.info("消息id:{},对应消息签收失败:{}",msgId,cause);
        }
    }

    /**
     * 更新消息表状态为签收
     * @param msgId
     */
    private void updateMsgStatusWithAck(String msgId){
        MessageContent messageContent = builderUpdateContent(msgId);
        messageContent.setMsgStatus(MsgStatusEnum.SENDING_SUCCESS.getCode());
        msgContentMapper.updateMsgStatus(messageContent);
    }


    /**
     * 更新消息表状态为拒绝签收
     * @param msgId
     */
    private void updateMsgStatusWithNack(String msgId){
        MessageContent messageContent = builderUpdateContent(msgId);
        messageContent.setMsgStatus(MsgStatusEnum.SENDING_FAIL.getCode());
        msgContentMapper.updateMsgStatus(messageContent);
    }


    /**
     * 生成通用修改消息状态对象的通用部分
     * @param msgId
     */
    private MessageContent builderUpdateContent(String msgId){
        MessageContent messageContent = new MessageContent();
        messageContent.setMsgId(msgId);
        messageContent.setUpdateTime(new Date());
        return messageContent;
    }
}
