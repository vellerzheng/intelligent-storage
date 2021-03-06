package com.mcloud.dbprovider.config;

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
public class RabbitMqConfig {
    /**
     * 消息交换
     */
    public final static String MESSAGE_EXCHANGE = "message.direct.exchange";
    /**
     * 消息队列名称
     */
    public final static String QUEUE_NAME = "message.queue";
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

}
