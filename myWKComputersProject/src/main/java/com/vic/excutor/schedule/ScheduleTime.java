package com.vic.excutor.schedule;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wangqp
 * @Date: 2018/8/23 16:58
 * @Description:
 */
public class ScheduleTime {
    public static void main(String[] args) {
//        Thread t = new Thread(r);
//        t.setName("DeadLockTimer");
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5);
        Runnable a = new Task();
        executorService.schedule(a,  10000, TimeUnit.MILLISECONDS);
        executorService.schedule(a, 3000, TimeUnit.MILLISECONDS);
        executorService.schedule(a,  20000, TimeUnit.MILLISECONDS);
//        executorService.scheduleWithFixedDelay(a,  20000, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        executorService.shutdown();
    }
    static class Task implements Runnable{
        public void run() {
            System.out.println(System.currentTimeMillis()+"   aaaaaa");
        }
    }
}
