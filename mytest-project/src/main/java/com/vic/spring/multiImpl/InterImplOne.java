package com.vic.spring.multiImpl;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-10 17:33
 **/
@Component
public class InterImplOne implements Inter{

    //@PostConstruct 会在 BeanPostProcessor 中被调用
    //@see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization
    @PostConstruct
    public void init(){
        System.out.println("InterImplOne");
    }

    @Override
    public void test() {
        //能继承 default 方法
        this.testDe();

    }
}
