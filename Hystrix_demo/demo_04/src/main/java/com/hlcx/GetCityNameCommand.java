package com.hlcx;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;


public class GetCityNameCommand extends HystrixCommand<String> {
    private Integer cityId;
    public GetCityNameCommand(Integer cityId) {
        // 设置信号量隔离策略
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetCityNameGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        //设置隔离级别：Semaphore(信号量)
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        //设置每组command可以申请的permit最大数
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(10)
                        //circuitBreaker打开后多久关闭
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)));
        this.cityId = cityId;
    }
    @Override
    protected String run() {
        // 需要进行信号量隔离的代码

        return LocationCache.getCityName(cityId);
    }
    @Override
    protected String getFallback(){
        return "I'm fallback";
    }


}
