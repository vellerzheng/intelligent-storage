package com.mcloud.fileserver.service.rabbitmq;


import com.alibaba.fastjson.JSON;
import com.mcloud.fileserver.config.RabbitMqConfig;
import com.mcloud.fileserver.repository.entity.FileHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:47 2018/5/24
 * @Modify By:
 */

@Service
public class RabbitMqProvider implements RabbitService {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(RabbitMqProvider.class);
    /**
     * 消息队列模板
     */

    @Autowired
    AmqpTemplate amqpTemplate;



    public void sendMessage(Object object) {
        logger.info("写入消息队列内容：{}", JSON.toJSONString(object));
        amqpTemplate.convertAndSend(RabbitMqConfig.FILE_EXCHANGE, RabbitMqConfig.ROUTE_KEY, object);
    }


    public void sendString(String fileHash){
        amqpTemplate.convertAndSend(RabbitMqConfig.FILE_EXCHANGE, RabbitMqConfig.ROUTE_KEY,fileHash);
    }



    public void sendFileHash(FileHash fileHash) {

        amqpTemplate.convertAndSend(RabbitMqConfig.FILE_EXCHANGE, RabbitMqConfig.ROUTE_KEY, fileHash);
    }

}
