package com.hlcx.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/demo_03")
public class Controller03 {

    @Value(value = "${server.port}")
    private String port;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/c")
    public String c(){
        return "Hello！I'm c. port：" + port;
    }



    @RequestMapping("/a")
    @HystrixCommand(fallbackMethod = "Fallback",//指定回调方法
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否开启断路器
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") //失败率达到多少后跳闸
            })//服务熔断
    /*熔断类型：
        打开：请求不再进行调用当前服务，当打开时长达到所设时钟进入半熔断状态
        关闭：熔断关闭不会对服务进行熔断
        半开：部分请求根据规则调用当前服务，如果请求成功且符合规则则认为当前服务恢复正常，关闭熔断
     */
    public String hi() {
        return restTemplate.getForObject("http://demo-01/a", String.class);
    }

    // 断路器回调方法
    public String Fallback() {
        return "I'm a";
    }



}
