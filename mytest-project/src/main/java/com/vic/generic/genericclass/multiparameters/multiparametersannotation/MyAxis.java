package com.vic.generic.genericclass.multiparameters.multiparametersannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//相当于标注，找到注解为 value 值的 泛型类
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_PARAMETER,ElementType.TYPE_USE})
public @interface MyAxis {
    String value() default "";
}
