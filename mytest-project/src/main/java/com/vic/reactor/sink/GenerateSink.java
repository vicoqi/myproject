package com.vic.reactor.sink;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GenerateSink {

    /**
     * public static <T> Flux<T> generate(Consumer<SynchronousSink<T>> generator)
     *
     *public static <T, S> Flux<T> generate(Callable<S> stateSupplier, BiFunction<S, SynchronousSink<T>, S> generator)
     *
     *public static <T, S> Flux<T> generate(Callable<S> stateSupplier, BiFunction<S, SynchronousSink<T>, S> generator, Consumer<? super S> stateConsumer)
     */

    /**
     * 用于计数；
     * 向“池子”放自定义的数据；
     * 告诉generate方法，自定义数据已发完；
     * 触发数据流。
     * 输出结果为每1秒钟打印一下时间，共打印5次。
     */
    @Test
    public void testGenerate1() {
        final AtomicInteger count = new AtomicInteger(1);   // 1
        Flux.generate(sink -> {
            sink.next(count.get() + " : " + new Date());   // 2
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count.getAndIncrement() >= 5) {
                sink.complete();     // 3
            }
        }).subscribe(System.out::println);  // 4
    }

    /**
     * 对于上边的例子来说，count用于记录状态，当值达到5之后就停止计数。
     * 由于在lambda内部使用，因此必须是final类型的，
     * 且不能是原生类型（如int）或不可变类型（如Integer）。
     */

    /**
     * 初始化状态值；
     * 第二个参数是BiFunction，输入为状态和sink；
     * 每次循环都要返回新的状态值给下次使用。
     */
    @Test
    public void testGenerate2() {
        Flux.generate(
                () -> 1,    // 1
                (count, sink) -> {      // 2
                    sink.next(count + " : " + new Date());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (count >= 5) {
                        sink.complete();
                    }
                    return count + 1;   // 3
                }).subscribe(System.out::println);
    }

    /**
     * 第三个方法签名除了状态、sink外，还有一个Consumer，这个Consumer在数据流发完后执行。
     * 如果 state 使用了数据库连接或者其他需要进行清理的资源，这个 Consumer lambda 可以用来在最后完成资源清理任务。
     */

    @Test
    public void testGenerate3() {
        Flux.generate(
                () -> 1,
                (count, sink) -> {
                    sink.next(count + " : " + new Date());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (count >= 5) {
                        sink.complete();
                    }
                    return count + 1;
                }, System.out::println)     // 1
                .subscribe(System.out::println);
    }
}
