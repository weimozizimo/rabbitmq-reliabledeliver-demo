package com.wyf.entity;


import lombok.Data;

import java.util.Date;

/**
*@Description 消息日志类
*@Author weiyifei
*@date 2021/6/16
*/
@Data
public class MessageContent {

    private String msgId;

    private long orderNo;

    private Date createTime;

    private Date updateTime;

    private Integer msgStatus;

    private String exchange;

    private String routingKey;

    private String errCause;

    private Integer maxRetry;

    private Integer currentRetry=0;

    private Integer productNo;
}
