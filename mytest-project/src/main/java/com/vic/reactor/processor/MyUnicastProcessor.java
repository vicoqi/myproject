package com.vic.reactor.processor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.UnicastProcessor;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

import javax.annotation.Nullable;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * UnicastProcessor
 *
 * UnicastProcessor可以使用一个内置的缓存来处理背压。代价就是它最多只能有一个订阅者（上一节的例子通过publish转换成了ConnectableFlux，所以可以接入两个订阅者）。
 *
 * UnicastProcessor有多种选项，因此提供多种不同的create静态方法。例如，它默认是 无限的（unbounded） ：如果你在在订阅者还没有请求数据的情况下让它推送数据，它会缓存所有数据。
 *
 * 可以通过提供一个自定义的 Queue 的具体实现传递给 create 工厂方法来改变默认行为。如果给出的队列是有限的（bounded）， 并且缓存已满，而且未收到下游的请求，processor 会拒绝推送数据。
 *
 * 在上边“有限的”例子中，还可以在构造 processor 的时候提供一个回调方法，这个回调方法可以在每一个 被拒绝推送的元素上调用，从而让开发者有机会清理这些元素。
 */
@Slf4j
public class MyUnicastProcessor {
    public static void main(String[] args) throws Exception{
        UnicastProcessor<Integer> processor = UnicastProcessor.create();
        FluxSink<Integer> sink = processor.sink(FluxSink.OverflowStrategy.BUFFER);
        processor.subscribe(new CoreSubscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe");
                s.request(Integer.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {

                System.out.println(integer);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("processor.onComplete()");
            }
        });
        sink.next(1);
        System.out.println(sink.currentContext().size());
        sink.complete();
        TimeUnit.SECONDS.sleep(2);
//        processor.onComplete();

//        testUnicastProcessor();
    }

    public void createDefault() {
        UnicastProcessor<Integer> processor = UnicastProcessor.create();
        assertProcessor(processor, null, null, null);
    }

    public void assertProcessor(UnicastProcessor<Integer> processor,
                                @Nullable Queue<Integer> queue,
                                @Nullable Consumer<? super Integer> onOverflow,
                                @Nullable Disposable onTerminate) {
        Queue<Integer> expectedQueue = queue != null ? queue : Queues.<Integer>unbounded().get();
        Disposable expectedOnTerminate = onTerminate;
        /*
        assertEquals(expectedQueue.getClass(), processor.queue.getClass());
        assertEquals(expectedOnTerminate, processor.onTerminate);
        if (onOverflow != null)
            assertEquals(onOverflow, processor.onOverflow);
            */
    }

//https://segmentfault.com/a/1190000012881468
    public static void testUnicastProcessor() throws InterruptedException {
        UnicastProcessor<Integer> unicastProcessor = UnicastProcessor.create(Queues.<Integer>get(8).get());
//        unicastProcessor.publishOn(new ExecutorScheduler());
        Flux<Integer> flux = unicastProcessor
                .map(e -> e)
                .doOnError(e -> {
                    log.error(e.getMessage(),e);
                });

        IntStream.rangeClosed(1,12)
                .forEach(e -> {
                    log.info("emit:{}",e);
                    unicastProcessor.onNext(e);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                });
        log.info("begin to sleep 7 seconds");
        TimeUnit.SECONDS.sleep(7);
        //UnicastProcessor allows only a single Subscriber
        flux.subscribe(e -> {
            log.info("flux subscriber:{}",e);
        });

        unicastProcessor.onComplete();
        TimeUnit.SECONDS.sleep(10);
//        unicastProcessor.blockLast(); //blockLast也是一个subscriber
    }

    @Test
    public void testDisposableUnicastProcessor(){
        UnicastProcessor<Integer> unicastProcessor = UnicastProcessor.create(Queues.<Integer>get(2).get(), System.out::println);
        Flux<Integer> flux = unicastProcessor
                                        .map(e -> e).publish()
                                        .doOnError(e -> {
                                            log.error(e.getMessage(),e);
                                        });

    }

}
