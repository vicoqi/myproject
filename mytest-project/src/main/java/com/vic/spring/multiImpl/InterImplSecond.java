package com.vic.spring.multiImpl;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-10 17:36
 **/
@Component
public class InterImplSecond implements Inter {

    @PostConstruct
    public void init(){
        System.out.println("InterImplSecond");
    }

    @Override
    public void test() {

    }
}
