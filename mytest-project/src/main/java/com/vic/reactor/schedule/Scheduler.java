package com.vic.reactor.schedule;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Scheduler {

    @Test
    public void testScheduling() {
        Flux.range(0, 10)
                .log()
                .publishOn(Schedulers.newParallel("myParallel"))
                .log()
//                .subscribeOn(Schedulers.newElastic("myElastic"))
//                .log()
//                .blockLast()
        .subscribe(System.out::println)
                ;
    }

}
