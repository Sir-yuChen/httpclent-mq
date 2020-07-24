package com.boot.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.entity.User;
import com.boot.service.MQService;
import com.boot.utils.CheckSumBuilder;
import com.boot.utils.HttpClientUtil;
import com.boot.utils.SendEmail;
import com.boot.utils.common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MQServiceImpl implements MQService {

    @Resource
    AmqpTemplate amqpTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    //向mq hello队列生产消息
    @Override
    public void sendToMq(String message) {
        amqpTemplate.convertAndSend("hello",message);
        log.info("******定义一个字符串发送给mq--->serviceimpl:"+message.toString());
    }

    //向mq发送一个对象
    @Override
    public void sendToMqObj(User user) {
        amqpTemplate.convertAndSend("hello",user);
        log.info("******向mq发送一个对象--->serviceimpl:"+user.toString());
    }

    //发送短信验证码  掉用接口
    @Override
    public Map sendCaptcha(String phone) throws Exception {

        //hashMap存放 返回的信息
        HashMap<String, Object> hashMap = new HashMap<String,Object>();
        //存入redis   key 为验证码存在时间+手机号
        if (redisTemplate.hasKey(common.SMS_LOCK+phone)) {
            hashMap.put("code",2);
            hashMap.put("msg","不能再1分钟之内重复申请验证码");
            return hashMap;
        }
        //存放连接的一些信息 连接地址    密匙
        HashMap<String, Object> headers = new HashMap<String,Object>();

        headers.put("AppKey", common.APP_KEY);
        String nonce = UUID.randomUUID().toString().replaceAll("-","");
        headers.put("Nonce",nonce);

        String curTime = System.currentTimeMillis()+"";
        headers.put("CurTime",curTime);

        headers.put("CheckSum", CheckSumBuilder.getCheckSum(common.APP_SERCRET, nonce, curTime));

        //params存放前台 传的手机号  和发短信的模板号
        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("mobile",phone);
        params.put("templateid",common.TEMPLATEID);

        Random random = new Random();
        int rand =  random.nextInt(900000)+100000;
        log.info("***********随机短信验证码："+rand);
        params.put("authCode", rand);
        //发送post请求
        String post = HttpClientUtil.post(common.SMS_URL, params , headers);

        //请求返回的数据   转换
        JSONObject parseObject = JSON.parseObject(post);
        //code请求状态码
        int code = parseObject.getIntValue("code");

        if (code != 200) {
            hashMap.put("code",1);
            hashMap.put("msg","验证码发送失败");
            return hashMap;
        }
        //验证码缓存
        redisTemplate.opsForValue().set(common.SMS_CODE+phone,rand,5, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(common.SMS_LOCK+phone,"lock",60,TimeUnit.SECONDS);
        hashMap.put("code",0);
        hashMap.put("msg","验证码发送成功");
        return hashMap;
    }

    //调用天气接口        给MQ设置自动补偿机制 5次 （配置文件中）
    @Override
    public void httpclientWeather(String cityName) {

        //拼接接口所需要的的参数   到common公共类中取出
        String paramsStr = "showapi_appid="+common.SHOWAPI_APPID+
                            "&showapi_sign="+common.SHOWAPI_SIGN+
                            "&area="+cityName+
                            "&showapi_timestamp=20200724170505";
        log.info("*********天气接口需要的参数："+paramsStr);
        // 调用工具类中的方法   调用接口
        String result = HttpClientUtil.sendWeatherPost("https://route.showapi.com/9-2",paramsStr,"utf-8");
        log.info("*********天气接口返回的结果："+result);
    }

    //发送邮件  调用邮件工具类方法
    @Override
    public void sendMail(String mailName) {

        SendEmail sen = new SendEmail();
        sen.setReceiveMailAccount(mailName);//填收件人的邮箱地址
        sen.setInfo("我是你爹，我是你爹，收到请回复！");
        try {
            sen.Send();
            log.info("*********发送邮件成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
