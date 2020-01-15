package com.vic.redis.reactor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisDatasourceConfiguration {

    /*
     * Lettuce
     */
//    @Bean
//    public LettucePool lettucePool() {
//        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        poolConfig.setMinIdle(10);
//        LettucePool pool = new DefaultLettucePool("127.0.0.1", 6379,poolConfig);
//        return pool;
//    }
    @Bean
    public ReactiveRedisConnectionFactory lettuceConnectionFactory1() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("118.190.209.5", 6379);
        redisStandaloneConfiguration.setDatabase(0);
        redisStandaloneConfiguration.setPassword(RedisPassword.of("vic123456"));
        LettuceConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        return redisConnectionFactory;
    }

    @Bean
    public RedisConnectionFactory lettuceConnectionFactory2() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("118.190.209.5", 6379);
        redisStandaloneConfiguration.setDatabase(1);
        redisStandaloneConfiguration.setPassword(RedisPassword.of("vic123456"));
        RedisConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        return redisConnectionFactory;
    }

    /*
     * Jedis

    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //return new JedisConnectionFactory(sentinelConfig , poolConfig);

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        //jedisConnectionFactory.setDatabase(0);

        return jedisConnectionFactory;

    }
    */

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate() {
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory1(), RedisSerializationContext.string());
    }

    @Bean
    public RedisTemplate<String, String> stringRedisTemplate2() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(lettuceConnectionFactory2());
        return redisTemplate;
    }
}
