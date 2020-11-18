package com.vic.generic.genericmethod.mygenericmethod;

import java.lang.annotation.*;

@Inherited
//@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyParameterAnnotation {
}
