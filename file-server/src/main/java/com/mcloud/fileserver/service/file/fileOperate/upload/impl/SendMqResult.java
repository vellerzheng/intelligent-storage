package com.mcloud.fileserver.service.file.fileOperate.upload.impl;

import com.mcloud.fileserver.repository.entity.common.MessageEntity;
import com.mcloud.fileserver.service.rabbitmq.RabbitMqProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:00 2018/6/28
 * @Modify By:
 */

public class SendMqResult  {

    private MessageEntity messageEntity;


    private RabbitMqProvider rabbitMqService;

    void setMessageEntity(MessageEntity messageEntity){
        this.messageEntity = messageEntity;
        rabbitMqService.sendMessage(messageEntity);
    }




}
