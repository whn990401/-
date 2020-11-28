package com.hlcx.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

public class CacheCommand extends HystrixCommand<String> {
    private String key;
    private static final HystrixCommandKey KEY=HystrixCommandKey.Factory.asKey("CacheCommand");
    public CacheCommand(String key){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("hello")).
                andCommandKey(KEY));
        this.key=key;
    }
    @Override
    protected String getCacheKey(){
        return this.key=key;
    }
    public static void flushCache(String key){
        HystrixRequestCache.getInstance(KEY,
                HystrixConcurrencyStrategyDefault.getInstance()).clear(key);//清除缓存，先获得command然后把对应的key删除
    }


    @Override
    protected String run() throws Exception {
        return "hello,"+key;
    }


    @Override
    protected String getFallback(){

        return "i'm fallback";
    }
}
