package com.mcloud.dbprovider.service.rabbitmq;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mcloud.dbprovider.config.RabbitMqConfig;
import com.mcloud.dbprovider.repository.entity.User;
import com.mcloud.dbprovider.repository.rabbitmq.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:48 2018/5/24
 * @Modify By:
 */
@Component
@RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
public class RabitMqConsumer {

    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(RabitMqConsumer.class);

    @RabbitHandler
    public void handler(@Payload MessageEntity messageEntity) {
        logger.info("消费内容：{}", JSON.toJSONString(messageEntity));
        System.out.println("消费内容：" + JSON.toJSONString(messageEntity));
    }

    @RabbitHandler
    public JSONObject handler1(@Payload User user){
        logger.info("消费内容：{}", JSON.toJSONString(user));
        System.out.println("消费内容：" + JSON.toJSONString(user));
        //返回执行结果（成功，失败）和ID
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username","kraft");
        jsonObject.put("filePath","/opt/data");
        jsonObject.put("status",1);
        return jsonObject;

    }

}
