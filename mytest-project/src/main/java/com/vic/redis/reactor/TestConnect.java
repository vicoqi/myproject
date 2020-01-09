package com.vic.redis.reactor;

import com.vic.redis.reactor.interfaces.IReactiveRedisRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class TestConnect {
    @Resource
    private IReactiveRedisRepository redisRepository;
    @Resource
    RedisTemplate<String, String> stringRedisTemplate2;

    @PostConstruct
    public void text1() throws InterruptedException {
        System.out.println("0**"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
        redisRepository.saveData("aa","bb").subscribe(b->{
            System.out.println("return saveData"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
        });
        System.out.println("1**"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
        Thread.sleep(100);
        System.out.println("2**"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
        text2();
        System.out.println("3***"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
        text2();
        System.out.println("4***"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
    }

    public void text2() {
        redisRepository.findAll()
                //single() 会自动关闭，销毁
//                .publishOn(Schedulers.single())
                //newSingle 不会自动关闭销毁,等待新的任务来执行
//                .publishOn(Schedulers.newSingle("findAll-Thread"))
                .subscribe(v->{
            System.out.println(v+ "**** return findAll****"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
        });
    }


//    @PostConstruct
    public void text3() throws InterruptedException {
        System.out.println("0**"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
        stringRedisTemplate2.opsForValue().set("aa","bb");
        System.out.println("1**"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
        Thread.sleep(100);
        System.out.println("2**"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
        String v = stringRedisTemplate2.opsForValue().get("aa");
        System.out.println(v+ "**** return findAll****"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
        String v1 = stringRedisTemplate2.opsForValue().get("aa");
        System.out.println("3***"+ Thread.currentThread().getName()+" time:"+System.currentTimeMillis());
    }

}
