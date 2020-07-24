package com.boot.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mqconfig {

    //消息队列的配置类

    @Bean
    public Queue Queue() {
        //创建一个队列，队列名是hello
        return new Queue("hello");
    }

}
