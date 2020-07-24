package com.boot.controller;

import com.boot.service.MQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Slf4j
public class testMail {

    @Resource
    MQService mqService;

    //发送邮件
    @RabbitListener(queues = "mail")
    public void sendMail(String  mailName){

        log.info("*********发送邮件--》controller"+mailName);
        mqService.sendMail(mailName);
    }
}
