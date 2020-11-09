package com.vic.redis.zset;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: wangqp
 * @create: 2020-09-27 11:49
 */
public class UseZset {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");

        Jedis jedis = pool.getResource();

        jedis.zadd("zsetKey",3,"redis1");
        jedis.zadd("zsetKey",1,"redis2");
        jedis.zadd("zsetKey",2,"redis3");

        System.out.println(jedis.zrange("zsetKey",0,10));
    }
}
