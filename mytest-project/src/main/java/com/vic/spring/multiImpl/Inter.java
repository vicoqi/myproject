package com.vic.spring.multiImpl;

public interface Inter {
    void test();
    public default void testDe(){
        System.out.println("hello world");
    }
}
