package com.boot.controller;

import com.boot.entity.User;
import com.boot.service.MQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("mq")
@Slf4j
public class MQController {

    @Resource
    MQService mqService;

    //向消息队列中发送消息
    @RequestMapping("sendToMq")
    public void sendToMq(){
        //定义一个字符串发送给mq
        String message = "hello    MQ ";
        mqService.sendToMq(message);
        log.info("******定义一个字符串发送给mq--->controller:");
    }

    //向mq发送一个对象
    @RequestMapping("sendToMqObj")
    public String sendToMqObj(){

        User user = new User();
        user.setUserName("张三");
        user.setUserCode("200");
        user.setUserPwd("123456");
        user.setUserId(Long.valueOf(1));

        mqService.sendToMqObj(user);
        log.info("******向mq发送一个对象--->controller:");
        return "success";
    }



}
