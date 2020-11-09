package com.vic.excutor.schedule;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务需要实现 Comparable<> 接口
 * @author: wangqp
 * @create: 2020-08-13 15:51
 */
public class PriotyQueue {

    public void prioty(){
        ThreadPoolExecutor handlerExecutor = new ThreadPoolExecutor(3, 3, 20, TimeUnit.SECONDS, new PriorityBlockingQueue<>(1));

    }
}
