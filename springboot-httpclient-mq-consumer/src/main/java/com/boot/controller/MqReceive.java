package com.boot.controller;


import com.boot.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RabbitListener(queues = "hello")//监听消息队列 这个注解可以放class上也可以放方法上
public class MqReceive {


    //消费队列中的消息
    @RabbitHandler// @RabbitListener监听注解上class上 这方法上用@RabbitHandler注解
    public void receiveMessage(String message){
        log.info("**********消费队列中的消息信息:"+message);
    }

    //消费队列中的消息  传对象
    @RabbitListener(queues = "hello")
    public void receiveMessageObj(User user){
        log.info("**********消费队列中的对象信息:"+user.toString());
    }




}
