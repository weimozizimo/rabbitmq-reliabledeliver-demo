package com.wyf.mapper;


import com.wyf.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;


/**
*@Description 订单信息mapper
*@Author weiyifei
*@date 2021/6/19
*/
@Mapper
public interface OrderInfoMapper {

    int saveOrderInfo(OrderInfo orderInfo);
}
