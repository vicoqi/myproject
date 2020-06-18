package com.vic.spring.multiImpl;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-10 18:05
 **/
@Component
public class TestMultiDI {
    //测试多个实现就打开这个注释
//    @Resource
//    Inter inter;

    public TestMultiDI(){
        System.out.println("TestMultiDI construct");
    }


    @PostConstruct
    public void init(){
        System.out.println("TestMultiDI");
    }
}
