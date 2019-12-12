package com.vic.reactor.processor;

import io.reactivex.internal.schedulers.ExecutorScheduler;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.UnicastProcessor;
import reactor.util.concurrent.Queues;

import javax.annotation.Nullable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Slf4j
public class MyUnicastProcessor {
    public static void main(String[] args) throws Exception{
//        UnicastProcessor<Integer> processor = UnicastProcessor.create();
//        FluxSink<Integer> sink = processor.sink(overflowStrategy);

        testUnicastProcessor();

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


}
