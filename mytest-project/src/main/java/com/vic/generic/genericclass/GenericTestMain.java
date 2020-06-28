package com.vic.generic.genericclass;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-19 16:06
 **/
public class GenericTestMain {
    public static void main(String[] args) {
        GenericImpl generic = new GenericImpl();
        System.out.println(generic.getModelClass().getName());
    }

}
