package com.example.service;

import com.example.bean.Book;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error")
    public String hello() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class);
        return responseEntity.getBody();
    }

    @HystrixCommand
    public Future<Book> asyncTest(){
        return new AsyncResult<Book>() {
            @Override
            public Book invoke() {
                return restTemplate.getForObject("http://HELLO-SERVICE/asyncTest",Book.class);
            }
        };
    }

    @HystrixCollapser(batchMethod = "test11",collapserProperties = {@HystrixProperty(name ="timerDelayInMilliseconds",value = "10000")})
    public Future<Book> test10(Long id){
        return null;
    }

    @HystrixCommand
    public List<Book> test11(List<Long> ids){
        System.out.println("test11---------"+ids+"Thread.currentThread().getName():" + Thread.currentThread().getName());
        Book[] books = restTemplate.getForObject("http://HELLO-SERVICE/getbook6?ids={1}", Book[].class, StringUtils.join(ids, ","));
        return Arrays.asList(books);
    }


    public String error() {
        return "error";
    }
}
