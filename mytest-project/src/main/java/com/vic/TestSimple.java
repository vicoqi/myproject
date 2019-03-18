package com.vic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: wqp
 * @Date: 2019/2/26 19:44
 * @Description:
 */
public class TestSimple {
    public static void main(String[] args) {
        TestSimple c = new TestSimple();
        new TestSimple().c(c);
        System.out.println(c);
    }

    //String 形参改变 不会影响原来的
    public void b(String a){
        a = null;
    }

    public void c(TestSimple c){
        c = null;
    }

    public void a(){
        List<String> a = new LinkedList<>();
        List<String> b = new LinkedList<>();
        a.add("a");
        a.add("b");
        a.add("c");
        a.add("d");
        b.add("c");
        b.add("d");
        a.addAll(b);
        System.out.println(a.toString());
    }
}
