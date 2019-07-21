package com.next.springcloud.jiangzh.nextconsumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service(value = "consumerServiceOne")
public class ConsumerHelloServiceImpl implements ConsumerHelloServiceAPI {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String showHello(String message) {
        // remote call provider - 不需要进行服务发现【Ribbon帮助我们做完了】
        String uri = "/provider/sayHello?message="+message;
        String url = "http://helloService"+ uri;

        // http invoker
        String result = restTemplate.getForObject(url, String.class);

        System.err.println("result="+result);

        return result;
    }
}
