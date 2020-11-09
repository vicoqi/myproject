package com.vic.spring.cycle;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: wangqp
 * @create: 2020-07-19 17:14
 */
@Component
public class ClassB {
    @Resource
    ClassA a;

    public ClassB(){
        System.out.println("ClassB constructor");
    }

    public void b(){
        System.out.println("B 被 A 引用");
    }
}
