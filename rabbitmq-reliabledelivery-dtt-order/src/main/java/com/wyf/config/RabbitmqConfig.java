package com.wyf.config;

import com.wyf.constants.MqConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
*@Description rabbitmq相关的配置
*@Author weiyifei
*@date 2021/6/19
*/
@Configuration
public class RabbitmqConfig {


    /**
     * 配置交换器
     * @return
     */
    @Bean
    public DirectExchange orderToProductExchange(){
        DirectExchange directExchange = new DirectExchange(MqConst.ORDER_TO_PRODUCT_EXCHANGE_NAME, true, false);
        return directExchange;
    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue orderToProductQueue(){
        Queue queue = new Queue(MqConst.ORDER_TO_PRODUCT_QUEUE_NAME, true, false, false);
        return queue;
    }

    /**
     * 将队列和交换机通过routingkey绑定
     * @return
     */
    @Bean
    public Binding orderToProductBinding(){
        return BindingBuilder.bind(orderToProductQueue()).to(orderToProductExchange()).with(MqConst.ORDER_TO_PRODUCT_ROUTING_KEY);
    }




}
