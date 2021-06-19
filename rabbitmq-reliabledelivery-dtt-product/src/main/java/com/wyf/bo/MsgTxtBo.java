package com.wyf.bo;

import lombok.Data;

import java.io.Serializable;

/**
*@Description 信息文本对象
*@Author weiyifei
*@date 2021/6/3
*/
@Data
public class MsgTxtBo implements Serializable {


    //订单编号
    private long orderNo;

    //货物编号
    private int productNo;

    //消息id
    private String msgId;
}
