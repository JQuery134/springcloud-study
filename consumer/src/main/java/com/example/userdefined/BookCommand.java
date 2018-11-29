package com.example.userdefined;

import com.example.bean.Book;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

public class BookCommand extends HystrixCommand<Book>{
    private RestTemplate restTemplate;
    private Long id;

    public BookCommand(Setter setter,RestTemplate restTemplate,Long id) {
        super(setter);
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected Book run() throws Exception {
        return restTemplate.getForObject("http://HELLO-SERVICE/cacheTest/{1}",Book.class,id);
    }

    @Override
    protected Book getFallback() {
        Throwable executionException = getExecutionException();
        System.out.println(executionException.getMessage());
        return super.getFallback();
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }
}
