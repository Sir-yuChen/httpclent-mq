package com.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class sendMail {

    @Resource
    AmqpTemplate amqpTemplate;

    @RequestMapping("sendMail")
    public void sendMail(String mailName){
        log.info("**********发送邮件"+mailName);
        amqpTemplate.convertAndSend("mail",mailName);
    };

}
