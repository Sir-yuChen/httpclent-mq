package com.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class SendCaptcha {

    @Autowired
    AmqpTemplate amqpTemplate;

    @RequestMapping("sendCaptcha")
    @ResponseBody
    public void sendCaptcha(String phone){
        log.info("*********将从前台页面获取到的手机号发送给MQ："+phone);
        amqpTemplate.convertAndSend("captcha",phone);
    }
}
