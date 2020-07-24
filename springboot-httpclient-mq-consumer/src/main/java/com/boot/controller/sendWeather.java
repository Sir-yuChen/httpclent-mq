package com.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@ResponseBody
@Slf4j
public class sendWeather {

    @Resource
    AmqpTemplate rabbitTemplate;

    @RequestMapping("sendCity")
    public void sendCity(String cityName){
        log.info("************调用天气接口按照地区："+cityName);
        rabbitTemplate.convertAndSend("weather",cityName);
    }
}
