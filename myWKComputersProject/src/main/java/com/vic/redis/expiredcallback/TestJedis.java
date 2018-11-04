package com.vic.redis.expiredcallback;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestJedis {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "132.232.45.67");

        Jedis jedis = pool.getResource();
        jedis.set("notify", "测试：是否能失效自动回调");
        jedis.expire("notify", 10);
    }

}
