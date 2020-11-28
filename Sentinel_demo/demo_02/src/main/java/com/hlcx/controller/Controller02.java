package com.hlcx.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller02 {

    @SentinelResource(entryType = EntryType.IN)//入口资源
    @RequestMapping("/sys")
    public String key(){
        return "hello, sentinel自适应保护";
    }

    //定义系统规则，不需要调用，
    @PostConstruct
    private void initSystemRule() {
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setQps(2);//设置入口qps资源，每秒允许的最大请求数
        rules.add(rule);
        SystemRuleManager.loadRules(rules);//加载资源
    }

}
