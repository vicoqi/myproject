package com.vic.stopwatch;

import com.google.common.base.Stopwatch;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-06-18 14:12
 **/
public class StopWatch {
    public static void main(String[] args) {
        org.springframework.util.StopWatch stopWatch = new org.springframework.util.StopWatch("simpleStopWatch");
        //防止内存溢出，数百万量级的
        stopWatch.setKeepTaskList(false);

        stopWatch.start("main1");
        test();
        stopWatch.stop();
        System.out.println(stopWatch);
//        System.out.println("------");
//        System.out.println(stopWatch.prettyPrint());
        stopWatch.start("main2");
        test();
        stopWatch.stop();
        System.out.println(stopWatch);

    }

    public static void test(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
