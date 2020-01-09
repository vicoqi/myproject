package com.vic.redis.reactor.interfaces;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IReactiveRedisRepository {

    Mono<Boolean> saveData(String key,String value);

    Flux<String> findAll();

    Mono<String> findValueByKey(String key);
}
