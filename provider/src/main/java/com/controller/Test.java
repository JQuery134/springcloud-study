package com.controller;

import com.example.bean.Book;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Test {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/hello")
    public String hello(){
        List<ServiceInstance> instances = discoveryClient.getInstances("hello-service");
        for (ServiceInstance serviceInstance : instances) {
            logger.info(serviceInstance.getServiceId());
        }
        return "hello";
    }
    @RequestMapping(value = "/asyncTest")
    public Book asyncTest(){
        return new Book("西游记","吴承恩");
    }
    @RequestMapping(value = "/cacheTest/{id}")
    public Book cacheTest(@PathVariable Long id){
        System.out.println(">>>>>>>>/cacheTest/{id}");
        if (id == 1) {
            return new Book("《李自成1》", "姚雪垠");
        } else if (id == 2) {
            return new Book("中国文学简史2","林庚");
        }
        return new Book("文学改良刍议3","胡适");
    }
    @RequestMapping("/getbook6")
    public List<Book> getbook6(String ids) {
        System.out.println("ids>>>>>>>>>>>>>>>>>>>>>" + ids);
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("《李自成》","姚雪垠"));
        books.add(new Book("中国文学简史", "清华大学出版社"));
        books.add(new Book("文学改良刍议", "无"));
        books.add(new Book("ids", "haha"));
        return books;
    }
}