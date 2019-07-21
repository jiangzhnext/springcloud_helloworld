package com.next.springcloud.jiangzh.ribbondemo;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.client.http.RestClient;

import java.io.IOException;
import java.net.URI;

public class RibbonHelloWorld {

    public static void main(String[] args) throws Exception {
        // 读取配置文件
        ConfigurationManager.loadPropertiesFromResources("application.properties");

        // 打印了一下serverList内容
        System.err.println(ConfigurationManager.getConfigInstance().getProperty("jiangzh-client.ribbon.listOfServers"));

        // 真实调用
        RestClient client = (RestClient) ClientFactory.getNamedClient("jiangzh-client");
        // 具体访问
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("/")).build();
        for (int i = 0; i < 5; i++)  {
            HttpResponse response = client.executeWithLoadBalancer(request);
            System.err.println("Status code for " + response.getRequestedURI() + "  :" + response.getStatus());
        }

        /*
        1、Ribbon如何进行使用
        2、Ribbon可以在运行期动态修改ServerList
        ======================================================================================
         */
        ZoneAwareLoadBalancer lb = (ZoneAwareLoadBalancer) client.getLoadBalancer();
        System.err.println(lb.getLoadBalancerStats());
        ConfigurationManager.getConfigInstance().setProperty(
                "jiangzh-client.ribbon.listOfServers", "www.qq.com:80,www.taobao.com:80");
        System.err.println("changing servers ...");
        Thread.sleep(3000);
        for (int i = 0; i < 5; i++)  {
            HttpResponse response = client.executeWithLoadBalancer(request);
            System.err.println("Status code for " + response.getRequestedURI() + "  : " + response.getStatus());
        }
        System.out.println(lb.getLoadBalancerStats()); // 7

    }


}
