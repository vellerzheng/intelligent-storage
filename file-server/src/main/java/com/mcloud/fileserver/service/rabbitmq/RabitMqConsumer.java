package com.mcloud.fileserver.service.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.mcloud.fileserver.config.RabbitMqConfig;
import com.mcloud.fileserver.service.file.ReceivedRabbitmqRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    final static  ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();


    @RabbitHandler
    public JSONObject handler1(@Payload JSONObject jsonObject){

        singleThreadExecutor.execute(new ReceivedRabbitmqRunnable(jsonObject));

     //   logger.info("消费内容：{}", jsonObject.toJSONString());

        //返回执行结果（成功，失败）和ID
        JSONObject json = new JSONObject();
        json.put("userId",jsonObject.get("userId"));
        json.put("username",jsonObject.get("userName"));
        json.put("fileId",jsonObject.get("fileId"));
        json.put("status",1);
        return json;

    }

}
