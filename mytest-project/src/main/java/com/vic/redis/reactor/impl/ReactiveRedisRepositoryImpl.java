package com.vic.redis.reactor.impl;

import com.vic.redis.reactor.interfaces.IReactiveRedisRepository;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Repository
public class ReactiveRedisRepositoryImpl implements IReactiveRedisRepository {
    @Resource
    private ReactiveRedisTemplate<String,String> reactiveRedisTemplate;
    @Override
    public Mono<Boolean> saveData(String key,String value) {
        return reactiveRedisTemplate.opsForValue().set(key,value).log();
    }

    @Override
    public Flux<String> findAll() {
        return reactiveRedisTemplate.keys("*")
                .flatMap(key-> reactiveRedisTemplate.opsForValue().get(key)).log();
    }

    @Override
    public Mono<String> findValueByKey(String key) {
        return reactiveRedisTemplate.opsForValue().get(key);
    }
}
