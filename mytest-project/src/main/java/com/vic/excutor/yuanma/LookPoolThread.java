package com.vic.excutor.poolName;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PoolThreadName {
    private ThreadPoolExecutor handlerExecutor = new ThreadPoolExecutor(2, 2, 20, TimeUnit.SECONDS,
            new ArrayBlockingQueue(1),new DefaultThreadFactory());


    public static void main(String[] args) {
        new PoolThreadName().handlerExecutor.execute(()->{
            System.out.println(Thread.currentThread().getName());
        });
    }

    class DefaultThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "traffic-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}

