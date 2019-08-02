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
    public void execTest(){
        System.err.println("current Thread first:"+Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        CommandHelloWorld helloWorld = new CommandHelloWorld("Next学院万岁");
        String result = helloWorld.execute();
        System.out.println("result="+result+" , spending:"+ (System.currentTimeMillis() - startTime));
    }

    /*
        请求缓存
     */
    @Test
    public void requestCache(){
        // 请求缓存必须要有上下文开启和关闭
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        System.err.println("current Thread first:"+Thread.currentThread().getName());
        CommandHelloWorld h1 = new CommandHelloWorld("h1");
        String r1 = h1.execute();
        System.out.println("r1="+r1);
        CommandHelloWorld h2 = new CommandHelloWorld("h2");
        String r2 = h2.execute();
        System.out.println("r2="+r2);
        CommandHelloWorld h3 = new CommandHelloWorld("h1");
        String r3 = h3.execute();
        System.out.println("r3="+r3+" , isCache:"+h3.isResponseFromCache());

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

        System.out.println("spending-01:"+ (System.currentTimeMillis() - startTime));

        String result = future.get();
        System.out.println("result="+result+" , spending-02:"+ (System.currentTimeMillis() - startTime));
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
    public void observeTest()throws Exception {
        CommandHelloWorld helloWorld = new CommandHelloWorld("Next学院万岁");
        Observable<String> observe = helloWorld.observe();
        // 阻塞式使用
        String result = observe.toBlocking().single();
        System.out.println("result="+result);
        // 非阻塞式  run -> onNext
        observe.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.err.println("next: this is onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("next: s="+throwable.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.err.println("next: s="+s);
            }
        });
    }

    /*
        反应式， 触发式
     */
    @Test
    public void toObservableTest()throws Exception {
        CommandHelloWorld helloWorld = new CommandHelloWorld("Next学院万岁");
        Observable<String> toObservable = helloWorld.toObservable();
        // 阻塞式使用
        String result = toObservable.toBlocking().single();
        System.out.println("result="+result);
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
                System.err.println("next: s="+throwable.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.err.println("next: s="+s);
            }
        });
        // 让主线程休息两秒
        Thread.sleep(2000L);
    }

}
