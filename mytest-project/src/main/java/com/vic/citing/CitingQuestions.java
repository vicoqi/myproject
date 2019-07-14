package com.vic.citing;

//引用复制
//引用问题
public class CitingQuestions {
    public static void main(String[] args) {
        String a = "a";
        a(a);
        System.out.println(a);
        Integer b = new Integer(1);
        b(b);
        System.out.println(b);
    }

    public static void a(String a){
        a = "b";
    }
    public static void b(Integer b){
        b = new Integer(4);
    }
}
