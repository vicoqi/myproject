package com.vic.reactor.sink;


/**
 * create是一个更高级的创建Flux的方法，其生成数据流的方式既可以是同步的，也可以是异步的，并且还可以每次发出多个元素。
 *
 * create用到了FluxSink，后者同样提供 next，error 和 complete 等方法。
 * 与generate不同的是，create不需要状态值，另一方面，它可以在回调中触发多个事件（即使事件是发生在未来的某个时间）。
 */


import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * create 常用的场景就是将现有的 API 转为响应式，比如监听器的异步方法。
 */
public class CreateSink {

    /**
     * Flux.create的sink将一系列事件转换成异步的事件流：
     * @throws InterruptedException
     */
    @Test
    public void testCreate() throws InterruptedException {
        MyEventSource eventSource = new MyEventSource();    // 1
        Flux.create(sink -> {
                    eventSource.register(new MyEventListener() {    // 2
                        @Override
                        public void onNewEvent(MyEventSource.MyEvent event) {
                            sink.next(event);       // 3
                        }

                        @Override
                        public void onEventStopped() {
                            sink.complete();        // 4
                        }
                    });
                }
        ).subscribe(System.out::println);       // 5

        for (int i = 0; i < 20; i++) {  // 6
            Random random = new Random();
            TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            eventSource.newEvent(new MyEventSource.MyEvent(new Date(), "Event-" + i));
        }
        eventSource.eventStopped(); // 7
    }

    /**
     *
     * FluxSink还有onRequest方法，这个方法可以用来响应下游订阅者的请求事件。
     * 从而不仅可以像上一个例子那样，上游在数据就绪的时候将其推送到下游，同时下游也可以从上游拉取已经就绪的数据。
     * 这是一种推送/拉取混合的模式。比如：
     */

    /**
     * push方式，主动向下游发出数据；
     * 在下游发出请求时被调用；
     * 响应下游的请求，查询是否有可用的message。
     * @throws InterruptedException
     */
    @Test
    public void testCreate1() throws InterruptedException {
//        Flux<String> bridge = Flux.create(sink -> {
//            myMessageProcessor.register(
//                    new MyMessageListener<String>() {
//
//                        public void onMessage(List<String> messages) {
//                            for(String s : messages) {
//                                sink.next(s);   // 1
//                            }
//                        }
//                    });
//            sink.onRequest(n -> {   // 2
//                List<String> messages = myMessageProcessor.request(n);  // 3
//                for(String s : message) {
//                    sink.next(s);
//                }
//            });
//        ...
//        }
    }


}
