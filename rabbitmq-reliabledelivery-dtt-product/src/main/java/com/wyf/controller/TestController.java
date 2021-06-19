package com.wyf.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("mq")
public class TestController {

    @Autowired
    private MsgSender sender;

    @RequestMapping("test1")
    public String test1() throws JsonProcessingException {
        Map<String,Object> msgProp = new HashMap<>();
        msgProp.put("company","zimo");
        msgProp.put("name","wyf");

        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setUserName("wyf");
        order.setPayMoney(10000.00);
        order.setCreateDt(new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = objectMapper.writeValueAsString(order);

        sender.sendMsg(orderJson,msgProp);

        return "ok";
    }

    @RequestMapping("test2")
    public String test2() throws JsonProcessingException {
        Map<String,Object> msgProp = new HashMap<>();
        msgProp.put("company","zimo");
        msgProp.put("name","wyf");

        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setUserName("wyf");
        order.setPayMoney(10000.00);
        order.setCreateDt(new Date());


        sender.sendMsg(order);

        return "ok";
    }

    /**
     * 测试延时队列
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("test3")
    public String test3() throws JsonProcessingException {
        Map<String,Object> msgProp = new HashMap<>();
        msgProp.put("company","zimo");
        msgProp.put("name","wyf");

        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setUserName("wyf");
        order.setPayMoney(10000.00);
        order.setCreateDt(new Date());


        sender.sendDelayMsg(order);

        return "ok";
    }


}
