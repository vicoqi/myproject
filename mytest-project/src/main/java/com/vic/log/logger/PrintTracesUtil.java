package com.vic.log.logger;


import lombok.extern.log4j.Log4j2;

/**
 * 需要在log4j2.xml 中配置logger 对应的name pathTrace
 *
 * <logger name="pathTrace" level="DEBUG" additivity="false">
 *             <appender-ref ref="LOG_TRACE"/>
 * </logger>
 *
 * 日志生产工具
 */
@Log4j2(topic = "pathTrace")
public class PrintTracesUtil {

    public static void printTrace(String message){
        log.info(message);
    }

    public static void printPath(String agvCode, String curPoint) {
        log.info("{}|cur:{}|{}",agvCode,curPoint);
    }

}
