package com.wyf.enumration;

import lombok.Getter;

/**
*@Description 消息状态枚举
*@Author weiyifei
*@date 2021/6/16
*/
@Getter
public enum MsgStatusEnum {

    SENDING(0,"发送中"),

    SENDING_SUCCESS(1,"消息发送成功"),

    SENDING_FAIL(2,"消息消费成功"),

    CONSUMER_SUCCESS(3,"消费成功"),

    CONSUMER_FAIL(4,"消费失败");

    private Integer code;

    private String msgStatus;

    MsgStatusEnum(Integer code, String msgStatus) {
        this.code = code;
        this.msgStatus = msgStatus;
    }
}
