package com.next.springcloud.jiangzh.nextprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NextProviderOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(NextProviderOneApplication.class, args);
    }

}
