package com.wyf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductStartApplication.class)
public class Test {

    @Autowired
    private MsgSender msgSender;

    @org.junit.Test
    public void testMsgSender() throws JsonProcessingException, InterruptedException {
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

        msgSender.sendMsg(orderJson,msgProp);

        Thread.sleep(500000);

    }

}
