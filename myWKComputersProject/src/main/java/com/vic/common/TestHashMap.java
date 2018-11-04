package com.vic.common;

/**
 * @Date: 2018/10/11 17:19
 * @Description:
 */

import java.util.HashMap;
import java.util.Map;

/**
 *   遍历hashMap 的时候，同时增删hashMap ,看是否能引起问题
 */
public class TestHashMap {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap();
        map.put("a","a");
        map.put("b","b");
        map.put("c","c");
        map.put("d","d");
        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (String f:map.values()) {
                    System.out.println("value is :" + f);
                }
            }
        }).start();

        map.put("aa","d");
        map.put("da","d");
        map.put("dd","d");
        System.out.println("=============================");

    }
}
