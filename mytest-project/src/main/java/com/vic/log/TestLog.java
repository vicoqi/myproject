package com.vic.log;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: wqp
 * @Date: 2019/2/21 18:03
 * @Description:
 */
@Slf4j
public class TestLog {
    public void aa(){
//        String a = null;
//        log.info("a:{}aaa");
//        log.info("a:{}",a);
//        String b = "null";
//        log.info("a:{}",b);
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        System.out.println(a.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).collect(Collectors.toList()));
    }
    public static void main(String[] args) throws InterruptedException {
        log.info("abc|{}", "hello log4j2");
        while (true) {
            Thread.sleep(500);
            try {
                new TestLog().asd();
            } catch (Throwable throwable) {
                log.error("{}", "aa", throwable);
            }
        }
    }

    public void asd() throws Throwable {
        throw new NullPointerException("as");
    }

//    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
//    StackTraceElement element = stackTraceElements[3];

}
