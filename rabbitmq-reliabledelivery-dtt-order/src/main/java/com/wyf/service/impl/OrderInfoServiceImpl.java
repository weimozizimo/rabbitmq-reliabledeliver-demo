package com.wyf.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wyf.bo.MsgTxtBo;
import com.wyf.compent.MsgSender;
import com.wyf.constants.MqConst;
import com.wyf.entity.MessageContent;
import com.wyf.entity.OrderInfo;
import com.wyf.enumration.MsgStatusEnum;
import com.wyf.mapper.MsgContentMapper;
import com.wyf.mapper.OrderInfoMapper;
import com.wyf.service.IOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.text.resources.de.FormatData_de;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class OrderInfoServiceImpl implements IOrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private MsgContentMapper msgContentMapper;

    @Autowired
    private MsgSender msgSender;



    @Transactional
    @Override
    public void saveOrderInfo(OrderInfo orderInfo, MessageContent messageContent) {
        try{

            orderInfoMapper.saveOrderInfo(orderInfo);

            //插入消息表
            msgContentMapper.saveMsgContent(messageContent);


        }catch (Exception e){
            log.error("操作数据库失败：{}",e);
            throw new RuntimeException("操作数据库失败");
        }
    }

    @Override
    public void saveOrderInfoWithMessage(OrderInfo orderInfo) throws JsonProcessingException {
        MessageContent messageContent = builderMessageContent(orderInfo.getOrderNo(), orderInfo.getProductNo());

        //保存数据库
        saveOrderInfo(orderInfo,messageContent);

        //构建消息发送对象
        MsgTxtBo msgTxtBo = new MsgTxtBo();
        msgTxtBo.setMsgId(messageContent.getMsgId());
        msgTxtBo.setOrderNo(orderInfo.getOrderNo());
        msgTxtBo.setProductNo(orderInfo.getProductNo());

        //发送消息
        msgSender.sendMsg(msgTxtBo);
    }


    /**
     * 构件消息对象
     */
    private MessageContent builderMessageContent(long orderNo,Integer productNo){
        MessageContent messageContent = new MessageContent();
        String msgId = UUID.randomUUID().toString();
        messageContent.setMsgId(msgId);
        messageContent.setCreateTime(new Date());
        messageContent.setUpdateTime(new Date());
        messageContent.setExchange(MqConst.ORDER_TO_PRODUCT_EXCHANGE_NAME);
        messageContent.setRoutingKey(MqConst.ORDER_TO_PRODUCT_EXCHANGE_NAME);
        messageContent.setMsgStatus(MsgStatusEnum.SENDING.getCode());
        messageContent.setOrderNo(orderNo);
        messageContent.setProductNo(productNo);
        messageContent.setMaxRetry(MqConst.MSG_RETRY_COUNT);
        return messageContent;
    }
}
