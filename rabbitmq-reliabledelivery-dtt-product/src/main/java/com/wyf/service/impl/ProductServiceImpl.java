package com.wyf.service.impl;

import com.wyf.bo.MsgTxtBo;
import com.wyf.entity.MessageContent;
import com.wyf.enumration.MsgStatusEnum;
import com.wyf.exceptioin.BizExp;
import com.wyf.mapper.MsgContentMapper;
import com.wyf.mapper.ProductInfoMapper;
import com.wyf.service.IProdcutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
*@Description 货物业务实现层
*@Author weiyifei
*@date 2021/6/16
*/
@Service
@Slf4j
public class ProductServiceImpl implements IProdcutService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private MsgContentMapper msgContentMapper;


    @Transactional
    @Override
    public boolean updateProductStore(MsgTxtBo msgTxtBo) {
        boolean updateFlag = true;

        try{
            //更新库存
            productInfoMapper.updateProductStoreById(msgTxtBo.getProductNo());

            //更新消息表状态
            MessageContent messageContent = new MessageContent();
            messageContent.setMsgId(msgTxtBo.getMsgId());
            messageContent.setUpdateTime(new Date());
            messageContent.setMsgStatus(MsgStatusEnum.CONSUMER_SUCCESS.getCode());
            msgContentMapper.updateMsgStatus(messageContent);
        }catch (Exception e){
            log.error("更新数据库失败:{}",e);
            throw new BizExp(0,"更新数据库异常");
        }

        return updateFlag;
    }
}
