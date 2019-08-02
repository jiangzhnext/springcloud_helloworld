package com.next.jiangzh.springcloud.helloworld;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ObservableCommandHelloWorld extends HystrixObservableCommand<String> {

    private String name;

    public ObservableCommandHelloWorld(String name){
        // Hystrix必须写groupKey
        // 服务分组
        // 如果没有指定线程池，则会用groupKey标识一个线程池
        super(Setter.withGroupKey(
                HystrixCommandGroupKey
                        .Factory.asKey("commandHelloWorld")));
        this.name = name;
    }


    /*
        真实业务方法
     */
    @Override
    protected Observable<String> construct() {
        System.err.println("current Thread:"+Thread.currentThread().getName());
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                // 可以进行批量内容处理
                subscriber.onNext("hello: "+name);
                subscriber.onNext("next : "+name);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }


}
