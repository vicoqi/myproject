package com.vic.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-05-14 17:22
 **/
@Component
public class Test_one implements InitializingBean {

    public Test_one(){
        System.out.println("***start|construct");
    }

    @PostConstruct
    public void init(){
        System.out.println("***start|PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("***start|impl InitializingBean");
    }
}
