package com.vic.rxjava.optest;

import io.reactivex.Observable;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * @author: wangqp
 * @create: 2020-10-19 16:36
 */
public class FurtherTest {

    //把异步返回包装成流
    @Test
    public void test(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return " [Hello|"+Thread.currentThread().getName()+"] ";
        }, Executors.newFixedThreadPool(1, r -> {
            Thread t = new Thread(null, r,
                    " namePrefix + threadNumber.getAndIncrement() ",
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }));

        Observable<String> novel=Observable.create(emitter -> {
            //emitter.onNext("连载1");
            future.thenAccept(s-> emitter.onNext(s));
            System.out.println("emitter onNext complete"+"::: "+Thread.currentThread());
        });

        novel.subscribe(a -> {
            System.out.println("from subscribe| v:" + a + " {Thread:" + Thread.currentThread().getName());
        });

        System.out.println("CompletableFuture| "+Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
