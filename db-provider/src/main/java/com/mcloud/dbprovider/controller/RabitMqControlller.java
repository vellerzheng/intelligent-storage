package com.mcloud.dbprovider.controller;


import com.mcloud.dbprovider.repository.entity.User;
import com.mcloud.dbprovider.repository.rabbitmq.MessageEntity;
import com.mcloud.dbprovider.service.rabbitmq.RabitMqConsumer;
import com.mcloud.dbprovider.service.rabbitmq.RabitMqProvider;
import com.mcloud.dbprovider.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 12:39 2018/5/24
 * @Modify By:
 */
@RestController
public class RabitMqControlller {
    /**
     * 消息队列 - 消息提供者 注入
     */
    @Autowired
    private RabitMqProvider messageProvider;
    @Autowired
    private RabitMqConsumer messageConsumer;


    /**
     * 测试发送消息队列方法
     *
     * @param messageEntity 发送消息实体内容
     * @return
     */
    @RequestMapping(value = "/sendMessage",produces = {"application/json;charset=UTF-8"})
    public String sendMessage(MessageEntity messageEntity) {
        MessageEntity messageEntity1 =new MessageEntity();
        messageEntity.setContent("---------Hello RabbitMq.----------");

        User user = new User();
        user.setId(123456789);
        user.setUsername("vellerzheng");
        user.setEmail("342324@163.com");
        // 将实体实例写入消息队列
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for(int i=0;i<1000;i++){
            Thread thread = new Thread(new ExcuteThread(countDownLatch,messageEntity,user));
            countDownLatch.countDown();
            thread.start();
        }

        return "Success";
    }


    private class ExcuteThread implements  Runnable{

        private final CountDownLatch startLatch;
        private MessageEntity messageEntity;
        private User user;
        public ExcuteThread( CountDownLatch startLatch,MessageEntity messageEntity,User user){
            this.startLatch = startLatch;
            this.messageEntity = messageEntity;
            this.user = user;
        }
        @Override
        public void run(){
            try {
                startLatch.await(); //一直阻塞，直到计数器值为0
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
          //  messageProvider.sendMessage(messageEntity);

            Object object1 =  messageProvider.sendAndReceive(user);
            System.out.println("接收到的返回信息："+ object1);
        }
    }

}
