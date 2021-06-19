package com.wyf.constants;


/**
*@Description mq相关常量类
*@Author weiyifei
*@date 2021/6/19
*/
public class MqConst {

    /**
     * 交换机名称
     */
    public static final String ORDER_TO_PRODUCT_EXCHANGE_NAME = "order-to-product.exchange";

    /**
     * 队列名称
     */
    public static final String ORDER_TO_PRODUCT_QUEUE_NAME = "order-to-product.queue";

    /**
     * 路由key
     */
    public static final String ORDER_TO_PRODUCT_ROUTING_KEY = "order-to-product.key";

    /**
     * 消息重发最大次数
     */
    public static final Integer MSG_RETRY_COUNT = 5;

    /**
     * 消息重试时间间隔
     */
    public static final Integer TIME_DIFF = 300;

}
