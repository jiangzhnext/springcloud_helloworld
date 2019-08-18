package com.next.springcloud.jiangzh.nextprovider.controller;

import com.next.springcloud.jiangzh.nextprovider.service.ProviderHelloServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProviderController {

    @Autowired
    private ProviderHelloServiceAPI serviceAPI;

    @RequestMapping(value = "/provider/sayHello",method = RequestMethod.GET)
    public String sayHello(String message){
        System.err.println("now ProviderController-message"+message);

        return serviceAPI.showHello(message);
    }

    @RequestMapping(value = "/provider/sayHello/{nextMsg}",method = RequestMethod.POST)
    public String postTest(@PathVariable("nextMsg")String nextMsg,@RequestBody String bodyMsg){
        System.err.println("postTest-nextMsg:"+nextMsg + " , bodyMsg:"+bodyMsg);

        return serviceAPI.showHello(nextMsg);
    }

}
