package com.controller;

import com.example.bean.Book;
import com.example.service.HelloService;
import com.example.userdefined.BookCommand;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class Test {

    @Autowired
    private HelloService helloService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/hello")
    public String hello(){
        return helloService.hello();
    }
    @RequestMapping(value = "/asyncTest")
    public Book asyncTest() throws ExecutionException, InterruptedException {
        Future<Book> bookFuture = helloService.asyncTest();
        return bookFuture.get();
    }

    @RequestMapping(value = "/cacheTest")
    public Book cacheTest(){
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("commandKey");
        HystrixRequestContext.initializeContext();
        BookCommand bc1 = new BookCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("")).andCommandKey(commandKey), restTemplate, 1L);
        Book e1 = bc1.execute();
        BookCommand bc2 = new BookCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("")).andCommandKey(commandKey), restTemplate, 1L);
        Book e2 = bc2.execute();
        BookCommand bc3 = new BookCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("")).andCommandKey(commandKey), restTemplate, 1L);
        Book e3 = bc3.execute();
        System.out.println("e1:" + e1);
        System.out.println("e2:" + e2);
        System.out.println("e3:" + e3);
        return e1;
    }

    @RequestMapping(value = "/cacheTest1")
    @CacheResult
    public Book cacheTest1(Long id){
        HystrixRequestContext.initializeContext();
        Book book = restTemplate.getForObject("http://HELLO-SERVICE/cacheTest/{1}", Book.class, id);
        Book book1 = restTemplate.getForObject("http://HELLO-SERVICE/cacheTest/{1}", Book.class, id);
        Book book2 = restTemplate.getForObject("http://HELLO-SERVICE/cacheTest/{1}", Book.class, id+1);
        return book;
    }

    @RequestMapping(value = "/test12")
    public void test12() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Future<Book> f1 = helloService.test10(1l);
        Future<Book> f2 = helloService.test10(2l);
        Future<Book> f3 = helloService.test10(3l);
        Book b1 = f1.get();
        Book b2 = f2.get();
        Book b3 = f3.get();
        Thread.sleep(3000);
        Future<Book> f4 = helloService.test10(4l);
        Book b4 = f4.get();
        System.out.println("b1>>>"+b1);
        System.out.println("b2>>>"+b2);
        System.out.println("b3>>>"+b3);
        System.out.println("b4>>>"+b4);
        context.close();
    }

}
