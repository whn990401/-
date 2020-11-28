package com.hlcx.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Collections;

@RestController
public class Controller03 {
    @SentinelResource(value = "black",blockHandler = "fallback")
    @RequestMapping("/black")
    public String key(){
        return "hello, sentinel授权";
    }

    public String fallback(BlockException e){
        e.printStackTrace();
        return "没用权限";
    }


    //定义授权控制规则
    @PostConstruct
    private void whiteRule() {
        AuthorityRule rule = new AuthorityRule();
        rule.setResource("black");
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);//限制模式，AUTHORITY_BLACK 为黑名单模式，RuleConstant.AUTHORITY_WHITE为白名单模式
        rule.setLimitApp("192.168.1.59");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
        for (AuthorityRule authorityRule : AuthorityRuleManager.getRules()) {
            System.out.println(authorityRule.toString());
        }

    }


}
