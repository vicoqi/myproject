package com.vic.generic.genericmethod.interfaceparameter;

import com.vic.generic.genericmethod.mygenericmethod.MyMethodAnnotation;

/**
 * 方法泛型。
 * @author: wangqp
 * @create: 2020-06-19 17:00
 **/
public interface ISigin {

    //继承类可以在这个方法上加注解，匿名注解
    @MyMethodAnnotation
    void sigin(Object siginParam);
}
