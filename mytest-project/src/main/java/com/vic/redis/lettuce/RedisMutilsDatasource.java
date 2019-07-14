package com.vic.redis.lettuce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

//@Configuration
public class RedisMutilsDatasource {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Bean
    public RedisTemplate<String, String> stringRedisTemplate1() {
        LettuceConnectionFactory factory = (LettuceConnectionFactory) stringRedisTemplate.getConnectionFactory();
        factory.setDatabase(1);
        return new StringRedisTemplate(factory);
    }
}
