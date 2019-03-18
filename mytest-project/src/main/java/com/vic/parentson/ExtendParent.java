package com.vic.parentson;

/**
 * @Auther: wqp
 * @Date: 2018/12/4 11:44
 * @Description: 父类强转子类，可以表现子类的东西
 */
public class ExtendParent {
    public static void main(String[] args){
        Fruit f = new Apple("a",1);
        Object a = (Object)f;
        System.out.println(((Apple) a).getId());
//        System.out.println(a.getName());
        System.out.println(((Apple) a));
    }
}
