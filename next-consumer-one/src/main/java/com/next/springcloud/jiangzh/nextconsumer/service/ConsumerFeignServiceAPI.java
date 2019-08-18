package com.next.springcloud.jiangzh.nextconsumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "nextProvider",url = "http://localhost:8203")
public interface ConsumerFeignServiceAPI {

    @RequestMapping(value = "/provider/sayHello",method = RequestMethod.GET)
    String showHello(@RequestParam("message") String message);

    @RequestMapping(value = "/provider/sayHello/{nextMsg}",method = RequestMethod.POST)
    String postTest(@PathVariable("nextMsg")String nextMsg, @RequestBody String bodyMsg);

}
