package com.next.springcloud.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {

    @Value("${server.port}")
    private String port;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;

    @RequestMapping(value = "/config",method = RequestMethod.GET)
    public String configClient(){
        String msg = port+" , "+username+" , "+password;
        return  msg;
    }

}
