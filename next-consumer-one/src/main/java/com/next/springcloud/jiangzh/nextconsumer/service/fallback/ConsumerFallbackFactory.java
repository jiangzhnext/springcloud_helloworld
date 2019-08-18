package com.next.springcloud.jiangzh.nextconsumer.service.fallback;

import com.next.springcloud.jiangzh.nextconsumer.service.ConsumerFeignServiceAPI;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerFallbackFactory implements FallbackFactory<ConsumerFeignServiceAPI> {

    @Override
    public ConsumerFeignServiceAPI create(Throwable throwable) {
        // 生产一个fallback的实现类
        log.error("Now is ConsumerFallbackFactory, 原因：{}",throwable.getMessage());
        return new ConsumerFeignServiceAPI() {
            @Override
            public String showHello(String message) {
                System.err.println("Now is FallbackFactory showHello message:"+message);
                return "FallbackFactory message："+message;
            }

//            @Override
//            public String postTest(String nextMsg, String bodyMsg) {
//                return null;
//            }
        };
    }

}
