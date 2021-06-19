package com.wyf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wyf.entity.MessageContent;
import com.wyf.entity.OrderInfo;

/**
*@Description 订单服务业务逻辑类接口
*@Author weiyifei
*@date 2021/6/19
*/
public interface IOrderInfoService {

    /**
     * 订单保存
     * @param orderInfo
     * @param messageContent
     */
    void saveOrderInfo(OrderInfo orderInfo, MessageContent messageContent);

    void saveOrderInfoWithMessage(OrderInfo orderInfo) throws JsonProcessingException;
}
