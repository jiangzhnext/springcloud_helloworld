package com.next.springcloud.jiangzh.nextconsumer.service;

import com.next.springcloud.jiangzh.FeignConf;
import com.next.springcloud.jiangzh.nextconsumer.service.fallback.ConsumerFallbackFactory;
import com.next.springcloud.jiangzh.nextconsumer.service.fallback.ConsumerFallbackFeignServiceImpl;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "helloService",
        path = "/provider",
        configuration = FeignConf.class,
        fallbackFactory = ConsumerFallbackFactory.class)
public interface ConsumerFeignServiceAPI {

    @RequestMapping(value = "/sayHello",method = RequestMethod.GET)
    String showHello(@RequestParam("message") String message);

    @RequestMapping(value = "/sayHello/{nextMsg}",method = RequestMethod.POST)
    String postTest(@PathVariable("nextMsg")String nextMsg, @RequestBody String bodyMsg);

//    @RequestLine("GET /sayHello?message={message}")
//    String showHello(@Param("message") String message);

}
