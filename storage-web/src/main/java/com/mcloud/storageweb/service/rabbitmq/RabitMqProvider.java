package com.mcloud.storageweb.service.rabbitmq;


import com.alibaba.fastjson.JSON;
import com.mcloud.storageweb.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:47 2018/5/24
 * @Modify By:
 */
@Component
public class RabitMqProvider {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(RabitMqProvider.class);
    /**
     * 消息队列模板
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Object object) {
        logger.info("写入消息队列内容：{}", JSON.toJSONString(object));
        amqpTemplate.convertAndSend(RabbitMqConfig.MESSAGE_EXCHANGE, RabbitMqConfig.ROUTE_KEY, object);
    }

    public Object sendAndReceive(Object object){
       return amqpTemplate.convertSendAndReceive(RabbitMqConfig.MESSAGE_EXCHANGE, RabbitMqConfig.ROUTE_KEY, object);
    }

}
