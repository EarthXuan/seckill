package com.ex.seckill.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MQSender {
    private static Logger log= LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendSeckillMsg(SeckillMsg seckillMsg) {
        String msg=beanToString(seckillMsg);
        log.info("sendSeckill message"+msg);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE,msg);
    }

    /*public void send(Object message){
        String msg=beanToString(message);
        log.info("send message"+msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE,msg);
    }
    public void sendTopic(Object message){
        String msg=beanToString(message);
        log.info("send message"+msg);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key1",msg+"1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key2",msg+"2");
    }

	public void sendFanout(Object message) {
		String msg = beanToString(message);
		log.info("send fanout message:"+msg);
		amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
	}

	public void sendHeader(Object message) {
		String msg = beanToString(message);
		log.info("send fanout message:"+msg);
		MessageProperties properties = new MessageProperties();
		properties.setHeader("header1", "value1");
		properties.setHeader("header2", "value2");
		Message obj = new Message(msg.getBytes(), properties);
		amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
	}*/


    private <T> String beanToString(T value) {
        if(value==null){
            return null;
        }
        Class<?> clazz=value.getClass();
        if(clazz==int.class||clazz==Integer.class){
            return ""+value;
        }else if(clazz==String.class){
            return (String)value;
        }else if(clazz==long.class||clazz==Long.class){
            return ""+value;
        }else{
            return JSON.toJSONString(value);
        }
    }


}
