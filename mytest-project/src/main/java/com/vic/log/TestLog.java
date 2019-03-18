package com.vic.log;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: wqp
 * @Date: 2019/2/21 18:03
 * @Description:
 */
@Slf4j
public class TestLog {
    public void aa(){
        String a = null;
        log.info("a:{}aaa");
        log.info("a:{}",a);
        String b = "null";
        log.info("a:{}",b);
    }
    public static void main(String[] args) {
        new TestLog().aa();
    }
}
