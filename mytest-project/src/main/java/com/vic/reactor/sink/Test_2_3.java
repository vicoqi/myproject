package com.vic.reactor.sink;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Test_2_3 {

    private final int EVENT_DURATION   = 10;    // 生成的事件间隔时间，单位毫秒
    private final int EVENT_COUNT      = 20;    // 生成的事件个数
    private final int PROCESS_DURATION = 30;    // 订阅者处理每个元素的时间，单位毫秒

    private Flux<MyEventSource.MyEvent> fastPublisher;
    private SlowSubscriber slowSubscriber;
    private MyEventSource eventSource;
    private CountDownLatch countDownLatch;

    /**
     * 准备工作。
     */
    @Before
    public void setup() {
        countDownLatch = new CountDownLatch(1);
        slowSubscriber = new SlowSubscriber();
        eventSource = new MyEventSource();
    }


    /**
     * 调整不同的策略（BUFFER/DROP/LATEST/ERROR/IGNORE）观察效果，create方法默认为BUFFER；
     * 打印出每次的请求（也就是后边.publishOn的请求）；
     * 使用publishOn让后续的操作符和订阅者运行在一个单独的名为newSingle的线程上，第二个参数1是预取个数，
     * 也就是.publishOn作为订阅者每次向上游request的个数，默认为256，所以一定程度上也起到了缓存的效果，为了测试，设置为1。
     * 通常情况下，发布者于订阅者并不在同一个线程上，这里使用publishOn来模拟这种情况。
     */

    /**
     * 测试create方法的不同OverflowStrategy的效果。
     */
    @Test
    public void testCreateBackPressureStratety() {
        fastPublisher =
                createFlux(FluxSink.OverflowStrategy.BUFFER)    // 1
                        .onBackpressureBuffer()     // BUFFER
//                .onBackpressureDrop()     // DROP
//                .onBackpressureLatest()   // LATEST
//                .onBackpressureError()    // ERROR
                        .doOnRequest(n -> System.out.println("doOnRequest|===  request: " + n + " ==="))    // 2
                        .publishOn(Schedulers.newSingle("newSingle"), 1);   // 3
    }

    /**
     * 触发订阅，使用CountDownLatch等待订阅者处理完成。
     */
    @After
    public void subscribe() throws InterruptedException {
        fastPublisher.subscribe(slowSubscriber);
        generateEvent(EVENT_COUNT, EVENT_DURATION);
        countDownLatch.await(1, TimeUnit.MINUTES);
    }

    /**
     * 内部类，“慢的订阅者”。
     */
    class SlowSubscriber extends BaseSubscriber<MyEventSource.MyEvent> {

        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            request(1);     // 订阅时请求1个数据
        }

        @Override
        protected void hookOnNext(MyEventSource.MyEvent event) {
            System.out.println("          receive <<< " + event.getMessage()+" >>>thread "+Thread.currentThread());
            try {
                TimeUnit.MILLISECONDS.sleep(PROCESS_DURATION);
            } catch (InterruptedException e) {
            }
            request(1);     // 每处理完1个数据，就再请求1个
        }

        @Override
        protected void hookOnError(Throwable throwable) {
            System.err.println("hookOnError|receive <<< " + throwable);
        }

        @Override
        protected void hookOnComplete() {
            countDownLatch.countDown();
        }
    }

    /**
     * 使用create方法生成“快的发布者”。
     * @param strategy 回压策略
     * @return  Flux
     */
    private Flux<MyEventSource.MyEvent> createFlux(FluxSink.OverflowStrategy strategy) {
        return Flux.create(sink -> eventSource.register(new MyEventListener() {
            @Override
            public void onNewEvent(MyEventSource.MyEvent event) {
                System.out.println("publish >>> " + event.getMessage());
                sink.next(event);
            }

            @Override
            public void onEventStopped() {
                sink.complete();
            }
        }), strategy);  // 1
    }
    /**
     * 生成MyEvent。
     * @param times 生成MyEvent的个数。
     * @param millis 每个MyEvent之间的时间间隔。
     */
    private void generateEvent(int times, int millis) {
        // 循环生成MyEvent，每个MyEvent间隔millis毫秒
        for (int i = 0; i < times; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(millis);
            } catch (InterruptedException e) {
            }
            eventSource.newEvent(new MyEventSource.MyEvent(new Date(), "Event-" + i));
        }
        eventSource.eventStopped();
    }
}
