package com.vic.generic.genericmethod;

/**
 * 方法泛型。
 * @author: wangqp
 * @create: 2020-06-19 16:19
 **/
@Deprecated
public interface GenericInterface {
    <T extends ISigin> T c(T t);
}
