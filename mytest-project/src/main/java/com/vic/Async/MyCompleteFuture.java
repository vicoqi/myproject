package com.vic.Async;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*
runAsync(Runnable runnable)	使用ForkJoinPool.commonPool()作为它的线程池执行异步代码。
runAsync(Runnable runnable, Executor executor)	使用指定的thread pool执行异步代码。
supplyAsync(Supplier<U> supplier)	使用ForkJoinPool.commonPool()作为它的线程池执行异步代码，异步操作有返回值
supplyAsync(Supplier<U> supplier, Executor executor)	使用指定的thread pool执行异步代码，异步操作有返回值
*/
public class MyCompleteFuture {

    @Test
    public void test1(){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Hello");
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("CompletableFuture");
    }

    //有返回值得话，future.get() 会阻塞3秒
    @Test
    public void test2(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello|"+Thread.currentThread().getName();
        });

        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("CompletableFuture|"+Thread.currentThread().getName());
    }

    //不用 future.get() 完全异步链，就不会阻塞，在 main 线程
    @Test
    public void test3(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello|"+Thread.currentThread().getName();
        });
        future.thenAccept(s-> System.out.println("thenAccept|"+s));
        System.out.println("CompletableFuture|"+Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello|"+Thread.currentThread().getName();
        });
        CompletableFuture<String> a = future.thenApplyAsync(s-> "thenAccept|"+s);
        try {
            System.out.println(a.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("CompletableFuture|"+Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //可以扩展发射 sink
    @Test
    public void test5(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello|"+Thread.currentThread().getName();
        });

        Flux.create(sink -> future.thenAccept(s-> sink.next(s))
        ).subscribe(a->{
            System.out.println("from subscribe|||||v:"+a+"|||||Thread:"+Thread.currentThread().getName());
        });
        System.out.println("CompletableFuture|"+Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
