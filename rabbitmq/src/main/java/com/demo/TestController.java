package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private sender sender;

    @RequestMapping(value = "/hello")
    public String hello(){
        sender.send();
        return "sended";
    }
}
