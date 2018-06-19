package com.mcloud.storageweb.service.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.mcloud.storageweb.config.RabbitMqConfig;
import com.mcloud.storageweb.repository.entity.FileHash;
import com.mcloud.storageweb.repository.entity.common.MessageEntity;
import com.mcloud.storageweb.service.file.FileHashService;
import com.mcloud.storageweb.util.CustomDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;



/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:48 2018/5/24
 * @Modify By:
 */
@Component
@RabbitListener(queues = RabbitMqConfig.QUEUE_FILES, containerFactory="rabbitListenerContainerFactory")
public class RabitMqConsumer {

    @Autowired
    FileHashService fileHashService;
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
    public void handler1(@Payload String fileHash){

    //    fileHash.setCreatetime(CustomDateConverter.currentTime());
        System.out.println("##################################################");
     //   fileHashService.insert(fileHash);
        System.out.println("云文件与文件路径： " + JSON.toJSONString(fileHash));
        return ;

    }

}
