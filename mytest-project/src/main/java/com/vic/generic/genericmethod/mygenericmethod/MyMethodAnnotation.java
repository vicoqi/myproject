package com.vic.generic.genericmethod.mygenericmethod;

import java.lang.annotation.*;

//这对注解来说很重要，新建注解的时候
@Inherited
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyMethodAnnotation {
}
