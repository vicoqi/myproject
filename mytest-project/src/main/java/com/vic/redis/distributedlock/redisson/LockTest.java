package com.vic.redis.distributedlock.redisson;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @author: wangqp
 * @create: 2020-09-04 21:42
 */
public class LockTest {
    static  RedissonClient redisson;
    static {
        Config config = new Config();
        config.useClusterServers()
                // use "rediss://" for SSL connection
                .addNodeAddress("redis://127.0.0.1:6379");
        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);

        // Reactive API
        RedissonReactiveClient redissonReactive = Redisson.createReactive(config);

        // RxJava2 API
        RedissonRxClient redissonRx = Redisson.createRx(config);
    }

    //同步锁
    @Test
    public void syncTest(){
        RLock lock = redisson.getLock("myLock");

        // traditional lock method
//     1.   lock.lock();

        // or acquire lock and automatically unlock it after 10 seconds
//     2.  lock.lock(10, TimeUnit.SECONDS);

        // or wait for lock aquisition up to 100 seconds
        // and automatically unlock it after 10 seconds
        boolean res = false;
        try {
            res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (res) {
            try {
                System.out.println("get lock");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    //异步锁
    @Test
    public void asyncTest(){
        RLock lock = redisson.getFairLock("myLock");

//    1.    RFuture<Void> lockFuture = lock.lockAsync();

// or acquire lock and automatically unlock it after 10 seconds
//    2.   RFuture<Void> lockFuture = lock.lockAsync(10, TimeUnit.SECONDS);

// or wait for lock aquisition up to 100 seconds
// and automatically unlock it after 10 seconds
        RFuture<Boolean> lockFuture = lock.tryLockAsync(100, 10, TimeUnit.SECONDS);

        lockFuture.whenComplete((res, exception) -> {
            // ...
            lock.unlockAsync();
        });
    }


}
