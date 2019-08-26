package com.next.springcloud.jiangzh.nextconsumer.controller;

import com.next.springcloud.jiangzh.nextconsumer.service.ConsumerFeignServiceAPI;
import com.next.springcloud.jiangzh.nextconsumer.service.ConsumerHelloServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerHelloServiceAPI serviceAPI;

    @Autowired
    private ConsumerFeignServiceAPI feignServiceAPI;

    @RequestMapping(value = "/consumer/sayHello",method = RequestMethod.GET)
    public String sayHello(HttpServletRequest request,@RequestParam("message") String message){
        System.err.println("now Consumer feignServiceAPI Controller-message : "+message);

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headName = headerNames.nextElement();
            System.out.println(headName+" : "+request.getHeader(headName));
        }

        return feignServiceAPI.showHello(message);
    }

}
