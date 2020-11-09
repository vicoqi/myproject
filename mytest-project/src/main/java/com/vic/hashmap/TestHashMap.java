package com.vic.hashmap;

/**
 * @Date: 2018/10/11 17:19
 * @Description:
 */

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *   遍历hashMap 的时候，同时增hashMap ,看是否能引起问题
 */

/**
 * result :
 *  Exception in thread "Thread-0" java.util.ConcurrentModificationException
 */
public class TestHashMap {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap();
        map.put("a","a");
        map.put("b","b");
        map.put("c","c");
        map.put("d","d");
        if(map.isEmpty()){
            System.out.println("map.isEmpty()");
        }else {
            System.out.println("map.isnotEmpty()");
        }
        Map<String,String> map1 = new HashMap();
        map1.put(null,"aa");
        if(map1.isEmpty()){
            System.out.println("map.isEmpty()");
        }else {
            System.out.println("map.isnotEmpty()");
        }
        new Thread(()->{
            while (true) {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                for (String f:map.values()) {
                    System.out.println("value is :" + f);
                }
                System.out.println("for is end once");
            }
        }).start();

        for (int i = 0; i < 20; i++) {
            try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            map.put("aa"+i,""+i);
        }
        System.out.println("=============================");

    }

    @Test
    public void foreach(){
        HashMap hashMap = new HashMap();
//        hashMap.entrySet().forEach();
    }
}
