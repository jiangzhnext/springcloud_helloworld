package com.next.jiangzh.springcloud.helloworld;

import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

import java.util.Iterator;

public class ObservableCommandTest {

    @Test
    public void observableTest(){
        System.err.println("current Thread:"+Thread.currentThread().getName());
        ObservableCommandHelloWorld helloWorld = new ObservableCommandHelloWorld("SpringCloud万岁");
        Observable<String> observe = helloWorld.observe();
        // 阻塞式
        Iterator<String> iterator = observe.toBlocking().getIterator();
        while (iterator.hasNext()){
            System.out.println("result="+iterator.next());
        }
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
                System.err.println("observable: s="+s);
            }
        });
    }

}
