package com.controller;

import com.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @Autowired
    private HelloService helloService;
    @RequestMapping("/hello")
    public String hello() {
        return helloService.hello();
    }
}
