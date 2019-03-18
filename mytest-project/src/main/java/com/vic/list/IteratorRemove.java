package com.vic.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: wqp
 * @Date: 2019/1/8 10:24
 * @Description:
 */
public class IteratorRemove {
    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        a.add("a");
        a.add("b");
        a.add("c");
        Iterator<String> it = a.iterator();
        if(it.hasNext()){
            it.next();
            it.remove();
        }
        System.out.println(a);
    }
}
