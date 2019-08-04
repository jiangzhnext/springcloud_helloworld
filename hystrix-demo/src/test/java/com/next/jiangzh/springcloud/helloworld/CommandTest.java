package com.next.jiangzh.springcloud.helloworld;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CommandTest {

    /*
        Command同步调用
     */
    @Test
    public void execTest() {
        System.err.println("current Thread first:" + Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        CommandHelloWorld helloWorld = new CommandHelloWorld("Next学院万岁");
        String result = helloWorld.execute();
        System.out.println("result=" + result + " , spending:" + (System.currentTimeMillis() - startTime));
    }

    /*
       熔断方面的相关测试
     */
    @Test
    public void circuitBreakerTest() throws InterruptedException {
        // 请求时间窗  请求总数和请求失败阈值
        CommandHelloWorld c1 = new CommandHelloWorld("Next学院万岁");
        String r1 = c1.execute();
        System.out.println("r1=" + r1);

        CommandHelloWorld c2 = new CommandHelloWorld("jiangzh");
        String r2 = c2.execute();
        System.out.println("r2=" + r2);

        CommandHelloWorld c3 = new CommandHelloWorld("jiangzh");
        String r3 = c3.execute();
        System.out.println("r3=" + r3);

        // 如果是开启状态，五秒之内都会进入fallback方法
        Thread.sleep(1000);
        CommandHelloWorld c4 = new CommandHelloWorld("Next学院万岁");
        String r4 = c4.execute();
        System.out.println("r4=" + r4);

        // 如果触发熔断，5秒以后会进行半开启状态
        Thread.sleep(6000);
        CommandHelloWorld c5 = new CommandHelloWorld("jiangzh");
        String r5 = c5.execute();
        System.out.println("r5=" + r5);

        Thread.sleep(1000);
        CommandHelloWorld c6 = new CommandHelloWorld("c6");
        String r6 = c6.execute();
        System.out.println("r6=" + r6);

        // 半开启状态会间歇性释放请求之后面的业务调用

        // 如果说请求成功，则半开启状态会切换至关闭状态

        // 如果请求失败，则进行开启状态，五秒以后重试

    }

    /*
        请求缓存
     */
    @Test
    public void requestCache() {
        // 请求缓存必须要有上下文开启和关闭
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        System.err.println("current Thread first:" + Thread.currentThread().getName());
        CommandHelloWorld h1 = new CommandHelloWorld("h1");
        String r1 = h1.execute();
        System.out.println("r1=" + r1);
        CommandHelloWorld h2 = new CommandHelloWorld("h2");
        String r2 = h2.execute();
        System.out.println("r2=" + r2);
        CommandHelloWorld h3 = new CommandHelloWorld("h1");
        String r3 = h3.execute();
        System.out.println("r3=" + r3 + " , isCache:" + h3.isResponseFromCache());

        // 上下文关闭
        context.close();
    }

    /*
       Command异步调用
    */
    @Test
    public void queueTest() throws Exception {
        long startTime = System.currentTimeMillis();
        CommandHelloWorld helloWorld = new CommandHelloWorld("Next学院万岁");
        Future<String> future = helloWorld.queue();

        System.out.println("spending-01:" + (System.currentTimeMillis() - startTime));

        String result = future.get();
        System.out.println("result=" + result + " , spending-02:" + (System.currentTimeMillis() - startTime));
    }

    /*
        反应式， 触发式
        observe: Hot调用
            1、发现subscribe之后，直接先调用run方法
            2、加载 or 注册 subscribe方法
            3、调用onNext

        toObservable: Cold调用
            1、加载 or 注册 subscribe方法
            2、调用run方法
            3、调用onNext

     */
    @Test
    public void observeTest() throws Exception {
        CommandHelloWorld helloWorld = new CommandHelloWorld("Next学院万岁");
        Observable<String> observe = helloWorld.observe();
        // 阻塞式使用
        String result = observe.toBlocking().single();
        System.out.println("result=" + result);
        // 非阻塞式  run -> onNext
        observe.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.err.println("next: this is onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("next: s=" + throwable.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.err.println("next: s=" + s);
            }
        });
    }

    /*
        反应式， 触发式
     */
    @Test
    public void toObservableTest() throws Exception {
        CommandHelloWorld helloWorld = new CommandHelloWorld("Next学院万岁");
        Observable<String> toObservable = helloWorld.toObservable();
        // 阻塞式使用
        String result = toObservable.toBlocking().single();
        System.out.println("result=" + result);
        // 非阻塞式
        CommandHelloWorld helloWorld2 = new CommandHelloWorld("Next学院万岁");
        Observable<String> toObservable2 = helloWorld2.toObservable();
        toObservable2.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.err.println("next: this is onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("next: s=" + throwable.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.err.println("next: s=" + s);
            }
        });
        // 让主线程休息两秒
        Thread.sleep(2000L);
    }


    @Test
    public void threadPoolTest() throws InterruptedException {
        MyThread t1 = new MyThread("01");
        MyThread t2 = new MyThread("02");
        MyThread t3 = new MyThread("03");
        MyThread t4 = new MyThread("04");
        MyThread t5 = new MyThread("05");


        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        Thread.sleep(3000);
    }

    static class MyThread extends Thread {

        private String name;

        public MyThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            CommandHelloWorld helloWorld = new CommandHelloWorld(name);
            System.out.println("name=" + name + " , result=" + helloWorld.execute());
        }
    }

}


