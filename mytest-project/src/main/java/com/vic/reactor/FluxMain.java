package com.vic.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
@Slf4j
public class FluxMain {
    public static void main(String[] args) throws Exception{
        Flux.just("Hello", "World").log().subscribe(System.out::println);
        Flux<Integer> flux = Flux.fromArray(new Integer[] {1, 2, 3});
        flux.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(2);
        flux.subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
//        Flux.range(1, 10).subscribe(System.out::println);
//        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
//        Flux.intervalMillis(1000).subscribe(System.out::println);

        //校验流数据
//        StepVerifier.create(Flux.just("a", "b"))
//                .expectNext("a")
//                .expectNext("b")
//                .verifyComplete();
    }

    @Test
    public void testContext(){
        Flux<String> flux = Flux.just("Hello", "World")
                .subscriberContext(context -> context.put("1","hello"));
        CoreSubscriber<String> actual = new CoreSubscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("add subscriber");
                s.request(Integer.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        flux.subscribe(actual);
//        System.out.println(actual.currentContext().<String>get("1"));
        flux.subscribe(System.out::println);
    }
}
