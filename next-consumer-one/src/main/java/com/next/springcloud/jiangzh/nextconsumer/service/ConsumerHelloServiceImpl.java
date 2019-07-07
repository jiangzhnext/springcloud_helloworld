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

    @Autowired
    private LoadBalancerClient eurekaClient;

    @Override
    public String showHello(String message) {
        // get registry
        ServiceInstance instance = eurekaClient.choose("helloService");
        String hostName = instance.getHost();
        int port = instance.getPort();

        // remote call provider
        String uri = "/provider/sayHello?message="+message;
        String url = "http://"+ hostName +":"+ port + uri;

        // http invoker
        String result = restTemplate.getForObject(url, String.class);

        System.err.println("result="+result);

        return result;
    }
}
