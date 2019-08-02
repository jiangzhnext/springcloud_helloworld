package com.next.jiangzh.springcloud.helloworld;

import com.netflix.hystrix.*;
import org.assertj.core.util.Lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CommandCollapser extends HystrixCollapser<List<String>,String,Integer> {

    private Integer id;


    public CommandCollapser(Integer id){
        super(Setter
                .withCollapserKey(HystrixCollapserKey.Factory.asKey("collapserHello"))
                .andCollapserPropertiesDefaults(
                        HystrixCollapserProperties.defaultSetter()
                        .withTimerDelayInMilliseconds(1000)
                )
        );

        this.id = id;
    }

    /*
        请求参数
     */
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    /*
        具体的业务处理
     */
    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> collection) {
        return new BatchCommand(collection);
    }

    /*
        请求结果映射
     */
    @Override
    protected void mapResponseToRequests(List<String> strings, Collection<CollapsedRequest<String, Integer>> collection) {
        int count = 0;
        Iterator<HystrixCollapser.CollapsedRequest<String, Integer>> iterator = collection.iterator();
        while (iterator.hasNext()){
            HystrixCollapser.CollapsedRequest<String, Integer> response = iterator.next();
            String result = strings.get(count++);
            response.setResponse(result);
        }
    }

}

class BatchCommand extends HystrixCommand<List<String>>{

    private Collection<HystrixCollapser.CollapsedRequest<String, Integer>> collection;

    public BatchCommand(Collection<HystrixCollapser.CollapsedRequest<String, Integer>> collection){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchCommand")));
        this.collection = collection;
    }

    @Override
    protected List<String> run() throws Exception {

        System.err.println("currentThread="+Thread.currentThread().getName());

        List<String> result = Lists.newArrayList();
        Iterator<HystrixCollapser.CollapsedRequest<String, Integer>> iterator = collection.iterator();
        while (iterator.hasNext()){
            HystrixCollapser.CollapsedRequest<String, Integer> request = iterator.next();
            Integer argument = request.getArgument();
            result.add("Next学院 input:"+argument);
        }
        return result;
    }
}