package com.vic.log;

import com.vic.log.codeconfig.JavaConfig;
import com.vic.log.logger.PrintTracesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.message.ReusableSimpleMessage;
import org.apache.logging.log4j.spi.AbstractLoggerAdapter;
import org.apache.logging.log4j.spi.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: wqp
 * @Date: 2019/2/21 18:03
 * @Description:
 */
//@Slf4j
public class TestLog {
    private static final Logger log = LoggerFactory.getLogger(TestLog.class);
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
        PrintTracesUtil.printTrace("hope");
        log.info("hello log");
        log.info("abc|{}", "hello log4j2");
        getLogContext();
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

    public static void getLogContext(){
        /*
        1.失败，通过loggerAdapter 获取不到 logcontext ,方法都是protect
        AbstractLoggerAdapter<Logger> loggerAdapter = (AbstractLoggerAdapter<Logger>) LoggerFactory.getILoggerFactory();
        loggerAdapter.
        */
//        2.成功，通过这种方式，能得到当前的 LoggerContext,那应该可以在里面加些自己 config吧，不用通过 xml 吧。
        //应该可以通过代码 自定义appender 或者logger
        org.apache.logging.log4j.core.LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(TestLog.class.getClassLoader(), false);
        Configuration configuration = loggerContext.getConfiguration();


//        LoggerConfig myCodeConfig = new LoggerConfig("myCodeConfig",Level.DEBUG,false);
//        Appender appender = new Appender();
//        AppenderRef appenderRef = new AppenderRef();

//        myCodeConfig.addAppender();

//        configuration.addLogger("myCodeConfig",myCodeConfig);


        JavaConfig javaConfig  = new JavaConfig();
        javaConfig.customizedConfig();
        JavaConfig.print("my code customized log4j2");
    }

}
