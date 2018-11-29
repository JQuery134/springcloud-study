package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HelloController {

    @Value("${test}")
    private String test;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/hi")
    public String hi(){
        String[] activeProfiles = env.getActiveProfiles();
        for (String s : activeProfiles){
            System.out.println(s);
        }
        String property = env.getProperty("test", "默认值");
        return property;
    }
    @RequestMapping(value = "/test")
    public String test(){
        return this.test;
    }
}