package com.vic.guava.cache;

import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2018/10/11 14:07
 * @Description:
 */

public class TimedClean {
    public static void main(String[] args) {
        try {
            testEvictionByAccessTime();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void testEvictionByAccessTime() throws ExecutionException, InterruptedException {

        //put 或者 get 都会出发这个监听事件
        RemovalListener<String, Future> listener = notification ->
        {
            if (notification.wasEvicted()) {
                RemovalCause cause = notification.getCause();
                System.out.println("remove cacase is :" + cause.toString());
                System.out.println("remove key:" + notification.getKey() + "value:" + notification.getValue());
            }
        };

        LoadingCache<String, Future> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .removalListener(listener)// 添加删除监听
                .build(createCacheLoader());

//        cache.getUnchecked("wangji");  这个方法 会执行load 方法自动生成一个对象
        cache.put("wang",new Future("1","2","3"));
        TimeUnit.SECONDS.sleep(3);


        Future Future = null;
        if(cache.getIfPresent("wang1")!=null){
            Future = cache.get("wang1");
        }else{
//            cache.cleanUp();
            cache.put("wang1",new Future("1","2","3"));
        }
        TimeUnit.SECONDS.sleep(3000);
        System.out.println("是否为null：" );
//        cache.getUnchecked("guava");

//        TimeUnit.SECONDS.sleep(2);
//        Future = cache.getIfPresent("guava"); //不会重新加载创建cache
//        System.out.println("被销毁：" + (Future == null ? "是的" : "否"));
//
//        TimeUnit.SECONDS.sleep(2);
//        Future = cache.getIfPresent("guava"); //不会重新加载创建cache
//        System.out.println("被销毁：" + (Future == null ? "是的" : "否"));
//
//        TimeUnit.SECONDS.sleep(2);
//        Future = cache.getIfPresent("guava"); //不会重新加载创建cache
//        System.out.println("被销毁：" + (Future == null ? "是的" : "否"));
//
//        TimeUnit.SECONDS.sleep(2);
//        Future = cache.getIfPresent("guava"); //不会重新加载创建cache
//        System.out.println("被销毁：" + (Future == null ? "是的" : "否"));

    }

    public static com.google.common.cache.CacheLoader<String, Future> createCacheLoader() {
        return new com.google.common.cache.CacheLoader<String, Future>() {
            @Override
            public Future load(String key) throws Exception{
                System.out.println("加载创建key:" + key);
                return null;
            }
        };
    }
}

class Future{
    String a,b,c;
    public Future(String a,String b,String c){

    }
    @Override
    public String toString() {
        return "Future{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                '}';
    }
}
