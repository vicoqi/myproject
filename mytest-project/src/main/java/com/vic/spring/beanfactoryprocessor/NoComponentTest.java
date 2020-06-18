package com.vic.spring.beanfactoryprocessor;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-11 00:03
 **/
public class NoComponentTest {

    @PostConstruct
    public void init(){
        System.out.println("NoComponentTest init");
    }
}
