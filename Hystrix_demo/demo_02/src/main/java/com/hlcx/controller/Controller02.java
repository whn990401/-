package com.hlcx.controller;


import com.hlcx.command.CacheCommand;
import com.hlcx.command.Command01;
import com.hlcx.command.Command02;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Value;

import org.junit.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("demo_02")
public class Controller02 {
    @Value(value = "${server.port}")
    private String port;


    @RequestMapping("/b")
    public String b(){
        return "Hello！I'm b. port：" + port;
    }


    @RequestMapping("/hello")
    //同步执行
    public String hello(){
        String result = new Command01("world").execute();
        return result;

    }
    @RequestMapping("/hello/{name}")
    //异步执行
    public String hello_02(@PathVariable("name") String name) throws ExecutionException, InterruptedException {
        Future<String> result = new Command01(name).queue();//对command调用queue()，仅仅将command放入线程池的一个等待队列，就立即返回，拿到一个Future对象
        Future<String> result02 = new Command02(name).queue();
        return result.get()+" "+result02.get();

    }

    //上下文中多次请求同一个command
    @RequestMapping("/cache/{key}")
    public String cache(@PathVariable("key") String key){
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        CacheCommand command = new CacheCommand(key);
        Assert.assertEquals("hello,"+key,command.execute());
        Assert.assertFalse(command.isResponseFromCache());
        System.out.println("01完毕");

        CacheCommand command02 = new CacheCommand(key);
        Assert.assertEquals("hello,"+key,command02.execute());
        Assert.assertTrue(command02.isResponseFromCache());
        System.out.println("02完毕");

        CacheCommand.flushCache(key);//清除缓存

        CacheCommand command03 = new CacheCommand(key);
        Assert.assertEquals("hello,"+key,command03.execute());
        Assert.assertFalse(command03.isResponseFromCache());

        System.out.println("03完毕");
        context.shutdown();//command的调用

        return command.execute()+" "+command02.execute()+" "+command03.execute();
    }


}
