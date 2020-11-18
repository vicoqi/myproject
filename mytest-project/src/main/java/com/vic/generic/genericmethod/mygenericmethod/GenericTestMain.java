package com.vic.generic.genericmethod.mygenericmethod;

import org.springframework.core.annotation.AnnotationUtils;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-19 16:06
 **/
public class GenericTestMain {
    public static void main(String[] args) {
        GenericMethod genericMethod = new GenericMethod();
        genericMethod.c(new CommonObject());

        //得到这个类上的注解
//        AnnotationUtils.findAnnotation(spi, Spi.class);
    }

}
