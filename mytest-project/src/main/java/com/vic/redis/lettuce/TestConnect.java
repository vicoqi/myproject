package com.vic.redis.lettuce;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class TestConnect {
    @Resource
    RedisTemplate<String,String> stringRedisTemplate1;

    @Resource
    RedisTemplate<String,String> stringRedisTemplate2;

    @PostConstruct
    public void text1() {
        String key = "aa";
        Map<String,String> v = new HashMap<>();
        v.put("bb","cc");
        stringRedisTemplate1.opsForHash().putAll(key,v);
        text2();
    }

    public void text2() {
        String key = "aa2";
        Map<String,String> v = new HashMap<>();
        v.put("bb2","cc2");
        stringRedisTemplate2.opsForHash().putAll(key,v);
    }
}
