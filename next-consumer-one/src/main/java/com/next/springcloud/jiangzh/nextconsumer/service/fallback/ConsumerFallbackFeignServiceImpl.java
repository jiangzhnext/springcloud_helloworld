package com.next.springcloud.jiangzh.nextconsumer.service.fallback;

import com.next.springcloud.jiangzh.nextconsumer.service.ConsumerFeignServiceAPI;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class ConsumerFallbackFeignServiceImpl implements ConsumerFeignServiceAPI {

    @Override
    public String showHello(String message) {
        System.err.println("Now is fallback showHello message:"+message);
        return "fallback message："+message;
    }

    @Override
    public String postTest(String nextMsg, String bodyMsg) {
        return null;
    }
}
