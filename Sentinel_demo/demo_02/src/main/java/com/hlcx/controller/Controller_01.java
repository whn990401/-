package com.hlcx.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller_01 {

    @SentinelResource(value = "KEY",blockHandler = "fallback")//使用资源  value=资源名 blockHandler=回调方法
    @RequestMapping("/key")
    public String key(){
        return "hello, sentinel降级";
    }

    @SentinelResource(value = "KEY02",blockHandler = "fallback")//使用资源  value=资源名 blockHandler=回调方法
    @RequestMapping("/key02")
    public String key02(){
        int i=1/0;
        return "";
    }

    public String fallback(BlockException e){
        e.printStackTrace();
        return "系统繁忙";
    }



    //定义降级规则
    @PostConstruct
    private void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("KEY");//资源名
        rule.setCount(0.01);//阈值
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);//设置降级模式
        /*三种降级模式
            平均响应时间 (DEGRADE_GRADE_RT)：当资源的平均响应时间超过阈值时，进去准降级状态，
                接下来如果持续进入 5 个请求，平均响应时间超过阈值，则进入降级状态。
            异常比例 (DEGRADE_GRADE_EXCEPTION_RATIO)：当资源的每秒异常总数占通过量的比值超过阈值之后，资源进入降级状态
            异常数 (DEGRADE_GRADE_EXCEPTION_COUNT)：当资源近 1 分钟的异常数目超过阈值之后会进行熔断，
                如果降级时间设置小于60s，则有可能造成熔断结束后再次进入
         */
        rule.setTimeWindow(10);//设置降级的时间
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }



}
