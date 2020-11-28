package com.hlcx.controller;


import com.alibaba.csp.sentinel.*;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.hlcx.service.AsyncService;
import com.hlcx.service.Demo02Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private Demo02Service demo02Service;

    //抛出异常的方式定义资源
    @RequestMapping("/hello")
    public String hello()  {
        //使用代码中定义的限流规则
        try(Entry entry = SphU.entry("hello")) {
            return "hello,sentinel";
        } catch (BlockException e) {
            return "系统繁忙";
        }

    }

    @RequestMapping("/world")
    public String world()  {
        //使用控制台添加的限流规则
        try {
            SphU.entry("world");
            return "hello,sentinel";
        } catch (BlockException e) {
            return "系统繁忙";
        }

    }

    //返回布尔值方式定义资源
    @RequestMapping("/boolean")
    public Boolean aboolean()  {
        //使用控制台添加的限流规则
        if (SphO.entry("hello")){
            try{
                return true;
            }finally {
                SphO.exit();//释放资源
            }
        }else {
            return false;
        }

    }


    //异步调用限流
    @RequestMapping("/async")
    public void async(){
        AsyncEntry asyncEntry=null;
        try {
            asyncEntry = SphU.asyncEntry("hello");
            asyncService.hello();
        } catch (BlockException e) {
            System.out.println("系统繁忙");
        }finally {
            if (asyncEntry!=null){
                asyncEntry.exit();//释放资源
            }
        }
    }



    @SentinelResource(value = "hello",blockHandler = "fallback")//使用资源  value=资源名 blockHandler=回调方法
    @RequestMapping("/ann")
    public String ann(){
        return "hello, sentinel";
    }


    @RequestMapping("/test")
    public String test(){
        return demo02Service.key();
    }



    public String fallback(BlockException e){
        e.printStackTrace();
        return "系统繁忙";
    }




    //定义限流规则
    @PostConstruct//当前类构造函数执行完后执行
    public void initFlowRules(){
        List<FlowRule> ruleList = new ArrayList<>();
        FlowRule flowRule = new FlowRule();//创建限流规则
        flowRule.setResource("hello");//设置限制资源
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);//定义限流规则类型
        flowRule.setCount(2);//设置每秒可以通过的qps
        ruleList.add(flowRule);
        FlowRuleManager.loadRules(ruleList);//加载限流规则
    }

}
