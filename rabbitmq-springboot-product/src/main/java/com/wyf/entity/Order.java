package com.wyf.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Order {

    //订单id
    private String orderNo;
    //创建日期
    private Date createDt;
    //支付金额
    private double payMoney;
    //用户名称
    private String userName;



    public Order() {
    }

    public Order(String orderNo, Date createDt, double payMoney, String userName) {
        this.orderNo = orderNo;
        this.createDt = createDt;
        this.payMoney = payMoney;
        this.userName = userName;
    }
}
