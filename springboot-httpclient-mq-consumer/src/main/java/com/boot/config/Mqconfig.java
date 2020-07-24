package com.boot.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mqconfig {

    //消息队列的配置类
    @Bean
    public Queue QueueCaptcha() {
        //创建一个队列，验证码
        return new Queue("captcha");
    }
    @Bean
    public Queue QueueWeather() {
        //创建一个队列，掉用天气
        return new Queue("weather");
    }
    @Bean
    public Queue QueueMail() {
        //创建一个队列，调用邮件
        return new Queue("mail");
    }

}
