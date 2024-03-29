package com.next.springcloud.jiangzh.nextconsumer.config;

import com.netflix.loadbalancer.*;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public IRule iRule(){
        return new RoundRobinRule();
    }

//    @Bean
//    public IPing iPing(){
//        return new PingUrl(false,"/provider/sayHello?message=ping");
//    }

}
