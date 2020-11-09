package com.vic.spring.cycle;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 测试循环依赖，A -》 B   ，B  - 》 A
 * @author: wangqp
 * @create: 2020-07-19 17:13
 */
@Component
public class ClassA {
    @Resource
    ClassB  b;

    public ClassA(){
        System.out.println("ClassA constructor");
    }

    @PostConstruct
    public void init(){
        b.b();
    }

}
