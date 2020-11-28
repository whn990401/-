package com.hlcx.hystris;


import com.hlcx.hystris.impl.FallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "demo-02" , fallback = FallBack.class)
public interface Hystris01 {

    @RequestMapping("demo_02/b")
    String b();

}
