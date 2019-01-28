package com.ex.seckill.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.ex.seckill.domain.Order;
import com.ex.seckill.service.GoodsService;
import com.ex.seckill.service.OrderService;
import com.ex.seckill.service.SeckillService;
import com.ex.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillService seckillService;
    private static Logger log= LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues = MQConfig.SECKILL_QUEUE)
    public void receiveSeckill(String message){
        log.info("receiveSeckill message:"+message);
        SeckillMsg seckillMsg=stringToBean(message,SeckillMsg.class);
        //判断库存
        GoodsVo goods=goodsService.selectGoodVoById(seckillMsg.getGoodsVo().getId());
        if(null==goods){
            return ;
        }
        Order order=orderService.getSeckillOrderByUserIdGoodsId(seckillMsg.getUser().getId(),seckillMsg.getGoodsVo().getId());
        if(null!=order){
            return ;
        }
        seckillService.seckill(seckillMsg.getUser(),goods);

    }
    /*@RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        log.info("receive message:"+message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message){
        log.info("receive topic1 message:"+message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message){
        log.info("receive topic2 message:"+message);
    }

    @RabbitListener(queues=MQConfig.HEADER_QUEUE)
    public void receiveHeaderQueue(byte[] message) {
        log.info(" header  queue message:"+new String(message));
    }*/
    private <T> T stringToBean(String value,Class<T> clazz) {
        if(StringUtils.isEmpty(value)||value.length()<=0||clazz==null){
            return null;
        }
        if(clazz==int.class||clazz==Integer.class){
            return (T) Integer.valueOf(value);
        }else if(clazz==String.class){
            return (T)value;
        }else if(clazz==long.class||clazz==Long.class){
            return (T)Long.valueOf(value);
        }else{
            return JSON.toJavaObject(JSON.parseObject(value),clazz);
        }
    }
}
