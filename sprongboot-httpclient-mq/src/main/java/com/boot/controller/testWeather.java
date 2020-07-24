package com.boot.controller;

import com.boot.service.MQService;
import com.boot.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Slf4j
public class testWeather {

    //httpclient调用天气端口

    @Resource
    MQService mqService;

    @RabbitListener(queues = "weather")
    public void httpclientWeather(String cityName){
        log.info("***********获取mq中的城市名称调用天气接口："+cityName);
        mqService.httpclientWeather(cityName);
    }


    //测试调用 天气接口
    public static void main(String[] args)  {
        //这里需要替换为你自己的appid和secret，你可以在这里找到 https://www.showapi.com/console#/myApp
        String showapi_appid = "302341";
        String showapi_sign = "e55ecdfb59fc4e238c880c1925370312";
        String showapi_timestamp = "20200721153006";
        String showapi_res_gzip= "0";
        String area= "郑州";
        //拼接接口所需的参数
        String paramsStr = "showapi_appid="+showapi_appid+"&showapi_sign="+showapi_sign+"&showapi_timestamp=" + showapi_timestamp +"&showapi_res_gzip="+showapi_res_gzip+"&area="+area;
        // 调用工具类中的方法   调用接口
        String result = HttpClientUtil.sendWeatherPost("https://route.showapi.com/9-2",paramsStr,"utf-8");
        //得到返回参数
        System.out.println(result);
    }






}
