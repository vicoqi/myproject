package com.vic.spring.beanfactoryprocessor;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-11 10:51
 **/
@Component
public class NoComponentTestSecond {

    @Resource
    NoComponentTest componentTest;


    @PostConstruct
    public void init(){
        Assert.notNull(componentTest,"di NoComponentTest.class is failure");
        System.out.println("NoComponentTestSecond init");
    }
}
