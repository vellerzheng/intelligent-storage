package com.mcloud.storageweb.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:43 2018/5/24
 * @Modify By:
 */
@Configuration
public class RabbitMqConfig  {
    /**
     * 消息交换
     */
    public final static String MESSAGE_EXCHANGE = "message.direct.exchange";
    public final static String FILE_EXCHANGE = "file.direct.exchange";

    /**
     * 消息队列名称
     */
    public final static String QUEUE_NAME = "message.queue";
    public final static String QUEUE_FILES = "file.queue";

    /**
     * 消息路由键
     */
    public final static String ROUTE_KEY = "message.send";





    /**
     * 交换配置
     *
     * @return
     */
    @Bean
    public DirectExchange messageDirectExchange() {
        return (DirectExchange) ExchangeBuilder.directExchange(MESSAGE_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public DirectExchange fileDirectExchange() {
        return (DirectExchange) ExchangeBuilder.directExchange(FILE_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 消息队列声明
     *
     * @return
     */
    @Bean
    public Queue messageQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .build();
    }

    @Bean
    public Queue fileQueue(){
        return QueueBuilder.durable(QUEUE_FILES)
                .build();
    }

    /**
     * 消息绑定
     *
     * @return
     */
    @Bean
    public Binding messageBinding() {
        return BindingBuilder.bind(messageQueue())
                .to(messageDirectExchange())
                .with(ROUTE_KEY);
    }

    @Bean
    public Binding fileBinding() {
        return BindingBuilder.bind(fileQueue())
                .to(fileDirectExchange())
                .with(ROUTE_KEY);
    }



}
