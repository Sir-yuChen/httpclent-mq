package com.boot.service;

import com.boot.entity.User;

import java.util.Map;

public interface MQService {


    void sendToMq(String message);

    void sendToMqObj(User user);

    Map sendCaptcha(String phone) throws Exception;

    void httpclientWeather(String cityName);

    void sendMail(String mailName);

}
