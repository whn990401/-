package com.hlcx.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class Command02 extends HystrixCommand<String> {
    private final String name;
    public Command02(String name){
        super(HystrixCommandGroupKey.Factory.asKey("hello"));//自定义commdan group
        this.name=name;

    }

    @Override
    protected String run() throws Exception {
        return "hello,"+name;
    }
    @Override
    protected String getFallback(){

        return new Command01("world").execute();//多级fallback
    }
}
