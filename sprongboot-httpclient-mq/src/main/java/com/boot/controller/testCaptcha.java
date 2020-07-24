package com.boot.controller;

import com.boot.service.MQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Slf4j
public class testCaptcha {


    @Resource
    MQService mqService;

    //用户注册发送邮件验证码      调用网易云的API接口
    //发送短信验证码       从mq队列中拿到手机号
    @RabbitListener(queues = "captcha")
    public  void sendCaptcha(String phone){
        try {
            log.info("******网易云短信验证码发送--->controller:"+phone);
            //抛出最大异常
            mqService.sendCaptcha(phone);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
