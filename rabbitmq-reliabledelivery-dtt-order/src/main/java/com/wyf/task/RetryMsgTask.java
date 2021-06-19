package com.wyf.task;

import com.wyf.bo.MsgTxtBo;
import com.wyf.compent.MsgSender;
import com.wyf.constants.MqConst;
import com.wyf.entity.MessageContent;
import com.wyf.enumration.MsgStatusEnum;
import com.wyf.mapper.MsgContentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
*@Description 失败重试定时器
*@Author weiyifei
*@date 2021/6/19
*/
@Component
@Slf4j
public class RetryMsgTask {


    @Autowired
    private MsgSender msgSender;

    @Autowired
    private MsgContentMapper msgContentMapper;

    /**
     * 延时5s启动
     * 这周期10s偶从
     */
    @Scheduled(initialDelay = 5000,fixedDelay = 10000)
    public void retrySend(){
        System.out.println("-----------------------------------");
        //查询5分钟消息状态还没有完结的消息
        List<MessageContent> messageContentList = msgContentMapper.qryNeedRetryMsg(MsgStatusEnum.CONSUMER_FAIL.getCode(), MqConst.TIME_DIFF);
        for (MessageContent messageContent : messageContentList) {
            if(messageContent.getMaxRetry()>messageContent.getCurrentRetry()){
                MsgTxtBo msgTxtBo = new MsgTxtBo();
                msgTxtBo.setMsgId(messageContent.getMsgId());
                msgTxtBo.setProductNo(messageContent.getProductNo());
                msgTxtBo.setOrderNo(messageContent.getProductNo());
                //更新消息重试次数
                msgContentMapper.updateMsgRetryCount(msgTxtBo.getMsgId());
                msgSender.sendMsg(msgTxtBo);
            }
        }
    }
}
