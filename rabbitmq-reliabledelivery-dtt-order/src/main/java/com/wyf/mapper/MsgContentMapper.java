package com.wyf.mapper;


import com.wyf.entity.MessageContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sun.plugin2.message.Message;

import java.util.List;

/**
*@Description 消息内容mapper
*@Author weiyifei
*@date 2021/6/16
*/
@Mapper
public interface MsgContentMapper {

    int saveMsgContent(MessageContent messageContent);

    int updateMsgStatus(MessageContent messageContent);

    List<MessageContent> qryNeedRetryMsg(@Param("status") Integer status, @Param("timeDiff") Integer timeDiff);

    void updateMsgRetryCount(String msgId);

}
