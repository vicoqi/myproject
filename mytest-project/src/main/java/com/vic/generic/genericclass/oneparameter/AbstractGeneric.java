package com.vic.generic.genericclass.oneparameter;

import lombok.Getter;

import java.lang.reflect.ParameterizedType;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-19 16:07
 **/
public abstract class AbstractGeneric<T> {

    @Getter
    // 当前泛型真实类型的Class
    private final Class<T> modelClass;

    public AbstractGeneric(){
        ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass();
        modelClass = (Class<T>)parameterizedType.getActualTypeArguments()[0];
    }

    public abstract void abstractMethod(T t);

    public abstract T abstractReturnMethod(T t);

    public T getGeneric(T t){
        return t;
    }
}
