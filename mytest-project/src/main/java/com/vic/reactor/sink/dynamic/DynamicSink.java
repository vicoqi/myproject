package com.vic.reactor.sink;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class DynamicSink {

    @Test
    public void create() throws InterruptedException {
        SomeFeed<PriceTick> feed = new SomeFeed<>();
        Flux<PriceTick> flux =
                Flux.create(emitter ->
                {
                    SomeListener listener = new SomeListener() {
                        @Override
                        public void priceTick(PriceTick event) {
                            emitter.next(event);
                            if (event.isLast()) {
                                emitter.complete();
                            }
                        }

                        @Override
                        public void error(Throwable e) {
                            emitter.error(e);
                        }};
                    feed.register(listener);
                }, FluxSink.OverflowStrategy.BUFFER);

        ConnectableFlux<PriceTick> hot = flux.publish();

        hot.subscribe(priceTick -> System.out.printf("%s %4s %6.2f%n", priceTick
                .getDate(), priceTick.getInstrument(), priceTick.getPrice()));

        hot.subscribe(priceTick -> System.out.println(priceTick.getSequence() +
                ": " + priceTick.getInstrument()));
        hot.connect();
        Thread.sleep(5000);
    }
}

