package com.vic.mq.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Condition实现生产消费者模型
 * @author: wangqp
 * @create: 2020-08-10 14:01
 */

class Goods {
    private int maxCount;
    private String name;
    private int count;

    Lock lock = new ReentrantLock();

    Condition producterCondition = lock.newCondition();
    Condition consumerCondition = lock.newCondition();

    public Goods(int maxCount) {
        this.maxCount = maxCount;
    }

    //制造商品
    public void setGoods(String name) {
        lock.lock();
        try {
            while (count == maxCount) {
                System.out.println(Thread.currentThread().getName() + "商品已达到最大数量");
                try {
                    producterCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.name = name;
            count++;
            System.out.println(Thread.currentThread().getName() + "生产" + toString());
            consumerCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }
    //消费商品
    public void getGood() {
        lock.lock();
        try {
            while (count == 0) {
                System.out.println(Thread.currentThread().getName() + "已售罄");
                try {
                    consumerCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count--;
            System.out.println(Thread.currentThread().getName() + "消费了" + toString());
            producterCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Goods = " + name + "---" + " count = " + count;
    }
}

class Producter implements Runnable {
    private Goods goods;

    public Producter(Goods goods) {
        this.goods = goods;
    }

    @Override
    public void run() {
        while (true)
            this.goods.setGoods("特百惠");
    }
}

class Consumer implements Runnable {
    private Goods goods;

    public Consumer(Goods goods) {
        this.goods = goods;
    }

    @Override
    public void run() {
        while (true)
            this.goods.getGood();
    }
}

public class ConditionQueue {
    public static void main(String[] args) {
        Goods goods = new Goods(10);
        Producter producter = new Producter(goods);
        Consumer consumer = new Consumer(goods);
        List<Thread> list = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Thread thread = new Thread(producter, "生产者" + i);
            list.add(thread);
        }
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(consumer, "消费者" + i);
            list.add(thread);
        }
        for (Thread thread : list) {
            thread.start();
        }
    }
}


