package com.vic.spring.beanpostprocessor;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-10 17:15
 **/
@Component
public class TestBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName){

        return bean;
    }

}
