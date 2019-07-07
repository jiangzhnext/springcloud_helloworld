package com.next.springcloud.jiangzh.nextprovider.controller;

import com.next.springcloud.jiangzh.nextprovider.service.ProviderHelloServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @Autowired
    private ProviderHelloServiceAPI serviceAPI;

    @RequestMapping(value = "/provider/sayHello",method = RequestMethod.GET)
    public String sayHello(String message){
        System.err.println("now ProviderController-message"+message);

        return serviceAPI.showHello(message);
    }

}
