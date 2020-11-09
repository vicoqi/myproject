package com.vic.excutor.Lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: wangqp
 * @create: 2020-08-14 11:05
 */
public class ConditionDemo {
    volatile int key = 0;
    Lock l = new ReentrantLock();
    Condition c = l.newCondition();

    public static  void main(String[] args){
        ConditionDemo demo = new ConditionDemo();
        new Thread(demo.new A()).start();
        new Thread(demo.new B()).start();
    }

    class A implements Runnable{
        @Override
        public void run() {
            int i = 10;
            while(i > 0){
                l.lock();
                try{
                    if(key == 1){
                        System.out.println("A is Running");
                        i--;
                        key = 0;
                        //condition 指定唤醒
                        c.signal();
                        //signal 只是把 await线程从等待队列里放到了同步队里了，还是需要排队竞争锁的
                        System.out.println("A is signal");
                    }else{
                        c.awaitUninterruptibly();
                    }
                }
                finally{
                    l.unlock();
                    System.out.println("A is unlock");
                }
            }
        }

    }

    class B implements Runnable{
        @Override
        public void run() {
            int i = 10;
            while(i > 0){
                l.lock();
                try{
                    if(key == 0){
                        System.out.println("B is Running");
                        i--;
                        key = 1;
                        c.signal();
                        System.out.println("B is signal");
                    }else{
                        c.awaitUninterruptibly();
                    }

                }
                finally{
                    l.unlock();
                    System.out.println("B is unlock");
                }
            }
        }
    }
}
