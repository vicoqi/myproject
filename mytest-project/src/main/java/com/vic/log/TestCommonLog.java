package com.vic.log;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-05-09 10:52
 **/
@Log4j2(topic = "common")
public class TestCommonLog {
    public static void main(String[] args) {
//        while (true) {
            log.info("aaaaaaaaaa");
            log.info("aaaaaaaaaa");
//        }
    }
}
