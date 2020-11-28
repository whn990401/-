package com.hlcx.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class Command01 extends HystrixCommand<String> {
    private final String name;
    public Command01(String name){
        super(HystrixCommandGroupKey.Factory.asKey("hello"));//自定义commdan group
        this.name=name;

    }

    @Override
    protected String run() throws Exception {
        return "hello,"+name;
    }

    //如果run()执行失败回调getFallback();
    @Override
    protected String getFallback(){

        return "I'm fallback";
    }
}
