package com.wyf.config;

import com.wyf.compent.ConfimCallBack;
import com.wyf.compent.ReturnCallBack;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.HashMap;

/**
 * rabbitmq 配置类
 */
@Configuration
public class RabbitmqConfig {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    /**
     * 声明直连交换机交换机
     * durable 消息是否持久化
     * autoDelete 是否自动删除
     */
    @Bean
    public DirectExchange directExchange(){
        DirectExchange directExchange = new DirectExchange("springboot.direct.exchange", true, false);
        return directExchange;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setConfirmCallback(new ConfimCallBack());
        rabbitTemplate.setReturnCallback(new ReturnCallBack());
        //设置json转换器，如果设置了该项，则无需再提前进行json串转换
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * 声明延时交换机
     */
    @Bean
    public CustomExchange delayExchange(){
        HashMap<String, Object> args = new HashMap<>();
        args.put("x-delayed-type","direct");
        return new CustomExchange("delayExchange","x-delayed-message",true,false,args);
    }

    @Bean
    public Queue bootQueue(){
        Queue queue = new Queue("bootQueue", true, false, false);
        return queue;
    }


    @Bean
    public Queue delayQueue(){
        Queue queue = new Queue("delayQueue", true, false, false);
        return queue;
    }


    @Bean
    public Binding bootBinder(){
        return BindingBuilder.bind(bootQueue()).to(directExchange()).with("springboot.key");
    }

    @Bean
    public Binding delayBinder(){
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with("springboot.key").noargs();
    }

}
