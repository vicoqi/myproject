package com.vic.hashmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**  并发时 查询所有，计算耗时
 * @Auther: wqp
 * @Date: 2018/12/28 18:12
 * @Description:
 */
public class Concurrenthashmap {
    public static void main(String[] args) {
        Map<String,String> map = new ConcurrentHashMap<>();
        map.put("a","a");
        map.put("b","b");
        map.put("c","c");
        map.put("d","d");
        new Thread(()->{
            while (true) {
                long st = System.currentTimeMillis();
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Map.Entry<String,String> f:map.entrySet()) {
//                    System.out.print("value is :" + f.getKey()+"\t");
//                    System.out.println("value is :" + f.getValue());
                }
                System.out.println("for is end once:"+(System.currentTimeMillis()-st));
            }
        }).start();

        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long st = System.currentTimeMillis();
            map.put("aa"+i,""+i);
            System.out.println("=============================* put is end once:"+(System.currentTimeMillis()-st));
        }
        System.out.println("=============================");

    }
    /*
    public static void main(String[] args) {
        Map<Integer, String> pointLock = new ConcurrentHashMap<>();
        System.out.println(pointLock.putIfAbsent(1,"a")==null?"as":"asd");
        System.out.println(pointLock.putIfAbsent(1,"b"));

        System.out.println(pointLock.get(1));
    }
    */
}
