package com.demo;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class sender {
    @Autowired
    private AmqpTemplate amqpTemplate;
    public void send(){
        String msg = "hello rabbitmq"+new Date();
        System.out.println("Sender:"+msg);
        amqpTemplate.convertAndSend("hello",msg);
    }
}
