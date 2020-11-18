package com.vic.generic.genericmethod.interfaceparameter;

import com.vic.generic.genericmethod.mygenericmethod.MyMethodAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 貌似不能继承方法上的注解，只能继承类上的注解
 * @author: wangqp
 * @create: 2020-11-17 14:44
 */
public class TestMain {
    public static void main(String[] args) {
        Method[] methods = SiginImpl.class.getMethods();
        Method[] declaredMethods = SiginImpl.class.getDeclaredMethods();
        Annotation[] annotations = SiginImpl.class.getAnnotations();
        Annotation[] declaredAnnotations1 = SiginImpl.class.getDeclaredAnnotations();
        Class<?>[] declaredClasses = SiginImpl.class.getDeclaredClasses();
        for (Method m:methods){
            Annotation[] declaredAnnotations = m.getDeclaredAnnotations();
            Annotation[] annotations1 = m.getAnnotations();
            Annotation[][] parameterAnnotations = m.getParameterAnnotations();
            if (m.isAnnotationPresent(MyMethodAnnotation.class)){
                System.out.println(m.getName() + " is present");
            }
            m.getParameters();
        }
    }
}
