package com.vic.log.logger;


import com.vic.log.TestLog;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.message.ReusableSimpleMessage;

/**
 * 需要在log4j2.xml 中配置logger 对应的name pathTrace
 *
 * <logger name="pathTrace" level="DEBUG" additivity="false">
 *             <appender-ref ref="LOG_TRACE"/>
 * </logger>
 *
 * 日志生产工具
 */
//@Log4j2(topic = "pathTrace")
public class PrintTracesUtil {
    private static final Logger log = LogManager.getLogger("pathTrace");

    public static void printTrace(String m){
        org.apache.logging.log4j.core.LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(TestLog.class.getClassLoader(), false);
        Configuration configuration = loggerContext.getConfiguration();

        LoggerConfig myLoggerConfig = configuration.getLoggers().get("pathTrace");
        ReusableSimpleMessage message = new ReusableSimpleMessage();
        message.set("myMessage");
        //第一个参数只是标识名，第二个参数，是定位堆栈，即打印类的
        myLoggerConfig.log(PrintTracesUtil.class.getName(),"com.vic.log.logger.PrintTracesUtil",null, Level.INFO,message,null);
    }

    public static void printPath(String agvCode, String curPoint) {
        log.info("{}|cur:{}|{}",agvCode,curPoint);
    }

}
