package com.next.springcloud.jiangzh.nextprovider.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service(value = "providerServiceOne")
public class ProviderHelloServiceImpl implements ProviderHelloServiceAPI {

    @Value(value = "${server.port}")
    private String serverPort;

    @Override
    public String showHello(String message) {

        System.err.println("Hi, ProviderService "+serverPort+"-message="+message);

        return "Hi, ProviderService "+serverPort+"-message="+message;
    }
}
