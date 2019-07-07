package com.next.springcloud.jiangzh.nextconsumer.controller;

import com.next.springcloud.jiangzh.nextconsumer.service.ConsumerHelloServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerHelloServiceAPI serviceAPI;

    @RequestMapping(value = "/consumer/sayHello",method = RequestMethod.GET)
    public String sayHello(String message){
        System.err.println("now Consumer Controller-message : "+message);

        return serviceAPI.showHello(message);
    }

}
