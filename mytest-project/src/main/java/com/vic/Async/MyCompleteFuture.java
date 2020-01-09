package com.vic.Async;

import org.junit.Test;

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

}
