package com.vic.generic.genericmethod;

import com.vic.generic.genericclass.GenericImpl;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-19 16:06
 **/
public class GenericTestMain {
    public static void main(String[] args) {
        GenericMethod genericMethod = new GenericMethod();
        genericMethod.c(new CommonObject());
    }

}
