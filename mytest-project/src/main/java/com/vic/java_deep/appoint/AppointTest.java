package com.vic.java_deep.appoint;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: wangqp
 * @create: 2020-07-15 10:24
 */
public class AppointTest {
    public static void main(String[] args) {
        String b = "b";
        AppointTest appointTest = new AppointTest();
        appointTest.a(b);
        System.out.println(b);
        List<String> l = new LinkedList();
        l.add("asd");
        appointTest.a(l);
        System.out.println(l);

    }

    public <T> void a(T a){
        a = null;
    }
}
