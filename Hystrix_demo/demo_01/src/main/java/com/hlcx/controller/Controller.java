package com.hlcx.controller;

import com.hlcx.hystris.Hystris01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Controller {
    @Value(value = "${server.port}")
    private String port;
    @Autowired
    private Hystris01 hystris01;

    @RequestMapping("/a")
    public String a(){
        return "Hello！I'm a. port：" + port;
    }

    @RequestMapping("/b")
    public String useB(){
        return hystris01.b();
    }


}
