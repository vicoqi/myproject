package com.vic.redis.expiredcallback.subscriber;

import com.vic.redis.expiredcallback.listener.KeyExpiredListener;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ExpiredSubscriber {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "132.232.45.67");

        Jedis jedis = pool.getResource();
        jedis.psubscribe(new KeyExpiredListener(), "__key*__:*");

    }

}
