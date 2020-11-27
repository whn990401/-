package com.hlcx.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "demo-02")
public interface Demo02Service {


    @RequestMapping("/black")
    public String key();
}
