package com.vic.reactor.context;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoContext {
    @Test
    public void testSubscriberContext(){
        String key = "message";
        Mono<String> r = Mono.just("Hello")
                .flatMap( s -> Mono.subscriberContext()
                        .map( ctx -> s + " " + ctx.get(key)))
                .subscriberContext(ctx -> ctx.put(key, "World"));

        StepVerifier.create(r)
                .expectNext("Hello World")
                .expectComplete()
                .verify();
    }
}
