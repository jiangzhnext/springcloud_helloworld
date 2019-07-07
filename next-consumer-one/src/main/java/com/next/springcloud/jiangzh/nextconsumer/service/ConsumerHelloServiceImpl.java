package com.next.springcloud.jiangzh.nextconsumer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service(value = "consumerServiceOne")
public class ConsumerHelloServiceImpl implements ConsumerHelloServiceAPI {

    @Value(value = "${server.port}")
    private String serverPort;

    @Override
    public String showHello(String message) {

        System.err.println("Hi, ConsumerService "+serverPort+"-message : "+message);

        return "Hi, ConsumerService "+serverPort+"-message : "+message;
    }
}
