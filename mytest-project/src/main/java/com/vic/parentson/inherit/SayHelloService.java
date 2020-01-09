package com.vic.parentson.inherit;

public class SayHelloService extends AbstractSayHello{
    public static void main(String[] args) {
        new SayHelloService().sayHello();
    }

    public void sayHello(){
        super.sayHello();
    }

    @Override
    public void overrideMethod(){
        System.out.println("aaa--overrideMethod");
    }
}
