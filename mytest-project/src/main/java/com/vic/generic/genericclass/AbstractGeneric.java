package com.vic.generic.genericclass;

import lombok.Getter;

import java.lang.reflect.ParameterizedType;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-19 16:07
 **/
public abstract class AbstractGeneric<T> {

    @Getter
    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractGeneric(){
        ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass();
        modelClass = (Class<T>)parameterizedType.getActualTypeArguments()[0];
    }

    public abstract void a(T t);

    public abstract T b(T t);

    public T c(T t){
        return t;
    }
}
