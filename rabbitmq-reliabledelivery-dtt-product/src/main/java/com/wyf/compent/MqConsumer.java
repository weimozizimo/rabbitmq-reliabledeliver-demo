package com.wyf.compent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.sun.xml.internal.ws.api.client.WSPortInfo;
import com.wyf.bo.MsgTxtBo;
import com.wyf.entity.MessageContent;
import com.wyf.enumration.MsgStatusEnum;
import com.wyf.exceptioin.BizExp;
import com.wyf.mapper.MsgContentMapper;
import com.wyf.service.IProdcutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
*@Description 消费者
*@Author weiyifei
*@date 2021/6/16
*/
@Component
@Slf4j
public class MqConsumer {

    /**
     * 队列名称
     */
    public static final String ORDER_TO_PRODUCT_QUEUE_NAME = "order-to-prodcut.queue";

    public static final String LOCK_KEY = "lock_key";

    @Autowired
    private IProdcutService prodcutService;

    @Autowired
    private MsgContentMapper msgContentMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 消费则，修改库存，使用分布式说保证消费不重复消费
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = {ORDER_TO_PRODUCT_QUEUE_NAME})
    public void consumerMsgWithLock(Message message, Channel channel) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        MsgTxtBo msgTxtBo = objectMapper.readValue(message.getBody(), MsgTxtBo.class);

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        //此处如果要保证分布式锁的完善，可以使用redssion的分布式锁
        if(redisTemplate.opsForValue().setIfAbsent(LOCK_KEY+msgTxtBo.getMsgId(),msgTxtBo.getMsgId())){
            log.info("消费信息：{}",msgTxtBo);
            try{
                //更新消息业务表
                prodcutService.updateProductStore(msgTxtBo);
                //消息签收
                channel.basicAck(deliveryTag,false);
            }catch (Exception e){
                /**
                 * 更新数据库异常说明业务没有操作成功需要删除分布式锁
                 */
                if(e instanceof BizExp){
                    BizExp bizExp = (BizExp) e;
                    log.info("数据业务异常：{},即将删除分布式锁",bizExp.getErrMsg());
                    //删除分布式锁
                    redisTemplate.delete(LOCK_KEY+msgTxtBo.getMsgId());
                }
                //更新消息状态
                MessageContent messageContent = new MessageContent();
                messageContent.setMsgStatus(MsgStatusEnum.CONSUMER_FAIL.getCode());
                messageContent.setUpdateTime(new Date());
                messageContent.setMsgId(msgTxtBo.getMsgId());
                messageContent.setErrCause(e.getMessage());
                msgContentMapper.updateMsgStatus(messageContent);
                channel.basicReject(deliveryTag,false);
            }
        }else {
            log.warn("请不要重复消费消息{}",msgTxtBo);
            channel.basicReject(deliveryTag,false);
        }

    }

}
