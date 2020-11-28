package com.hlcx.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller04 {


    @RequestMapping("/param")
    @SentinelResource(value = "param",blockHandler = "handleBlockForTest")
    public String param(int age,String name) {
        return "访问成功";
    }


    public String handleBlockForTest(){
        return "访问失败";
    }



    //定义热点规则
    @PostConstruct
    public static void initParamFlowRule(){
        List<ParamFlowRule> rules = new ArrayList<>();
        ParamFlowRule rule = new ParamFlowRule();
        //阈值类型：只支持QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //阈值
        rule.setCount(2);
        //资源名
        rule.setResource("param");
        rule.setParamIdx(1);//指配热点参数的下标
        //统计窗口时间长度
        rule.setDurationInSec(10);

/*        //设置额外参数
        List<ParamFlowItem> items = new ArrayList<>();
        ParamFlowItem item = new ParamFlowItem();
        item.setClassType(int.class.getName());
        item.setCount(5);
        item.setObject("12");
        items.add(item);

        rule.setParamFlowItemList(items);*/
        rules.add(rule);
        ParamFlowRuleManager.loadRules(rules);
    }

}
