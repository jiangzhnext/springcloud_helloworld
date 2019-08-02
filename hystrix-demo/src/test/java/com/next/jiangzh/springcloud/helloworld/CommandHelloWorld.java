package com.next.jiangzh.springcloud.helloworld;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class CommandHelloWorld extends HystrixCommand<String> {

    private String name;

    public CommandHelloWorld(String name){
        // Hystrix必须写groupKey
        // 服务分组
        // 如果没有指定线程池，则会用groupKey标识一个线程池
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("commandHelloWorld"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("commandKeyName"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.defaultSetter()
                            .withRequestCacheEnabled(false)
                )
        );
        this.name = name;
    }

    /*
        业务实现方法
        影院服务 -> 订单服务 -> 查询已售座位信息
     */
    @Override
    protected String run() throws Exception {
        // 查询已售座位信息
        System.err.println("current Thread second:"+Thread.currentThread().getName());
        Thread.sleep(800l);

        return "Run method, name:"+name;
    }

    /*
        判断请求是否同一个key
     */
    @Override
    protected String getCacheKey() {
        return String.valueOf(name);
    }
}
