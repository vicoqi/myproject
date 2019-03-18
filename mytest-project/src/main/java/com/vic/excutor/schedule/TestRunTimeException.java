package com.vic.excutor.schedule;

import java.sql.SQLOutput;
import java.util.concurrent.*;

/**
 * @Auther: wqp
 * @Date: 2018/12/29 16:18
 * @Description:
 */
public class TestRunTimeException {
    public static void main(String[] args) {
        ThreadPoolExecutor handlerExecutor = new ThreadPoolExecutor(3, 3, 20, TimeUnit.SECONDS,
                new ArrayBlockingQueue(1));
        System.out.println("start"+handlerExecutor.getActiveCount());
        for(int i=0;i<20;i++){
            int a = i;
            handlerExecutor.execute(() -> {
                System.out.println("i=="+a);
                new RuntimeException("aa");
            });
        }

        System.out.println("end"+handlerExecutor.getActiveCount());
    }
}
