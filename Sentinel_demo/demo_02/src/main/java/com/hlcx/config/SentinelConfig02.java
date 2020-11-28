package com.hlcx.config;


import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.nacos.client.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;


@Component
public class SentinelConfig02 {

    @PostConstruct
    public void doinit(){
        WebCallbackManager.setRequestOriginParser(new RequestOriginParser() {
            @Override
            public String parseOrigin(HttpServletRequest httpServletRequest) {

                //System.out.println(httpServletRequest.getRemoteAddr());
                //return httpServletRequest.getServerName();//获取服务器的名称。
                return httpServletRequest.getRemoteAddr();//获取客户的ip地址
            }
        });


    }


}
