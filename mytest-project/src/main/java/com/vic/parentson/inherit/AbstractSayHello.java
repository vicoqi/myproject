package com.vic.parentson.inherit;

public abstract class AbstractSayHello implements ISayHello{

   public void sayHello(){
        System.out.println("hello");
       overrideMethod();
   }

    public void overrideMethod(){
        System.out.println("hello--overrideMethod");
    }
}
