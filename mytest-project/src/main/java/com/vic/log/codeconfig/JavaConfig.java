package com.vic.log.codeconfig;

import com.vic.log.TestLog;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.appender.RollingRandomAccessFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.ReusableSimpleMessage;


public class JavaConfig {

    public void customizedConfig(){
        org.apache.logging.log4j.core.LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(TestLog.class.getClassLoader(), false);
        Configuration configuration = loggerContext.getConfiguration();

        //单个日志文件大小
        TimeBasedTriggeringPolicy tbtp = TimeBasedTriggeringPolicy.newBuilder().withInterval(3).withModulate(true).build();
        SizeBasedTriggeringPolicy tp = SizeBasedTriggeringPolicy.createPolicy("100 MB");// 大小分割
        CompositeTriggeringPolicy policyComposite = CompositeTriggeringPolicy.createPolicy(tbtp, tp);

        Appender appender = RollingRandomAccessFileAppender.newBuilder()
                .withName("MY_APPENDER").withFileName("logs/myAppender.log")
                .withFilePattern("logs/myAppender.log.%d{yyyy-MM-dd-HH}-%i.gz")
                .withLayout(PatternLayout.newBuilder().withPattern(PatternLayout.SIMPLE_CONVERSION_PATTERN).withConfiguration(configuration).build())
                .withPolicy(policyComposite)
                .setConfiguration(configuration)
                .build();
        appender.start();

        LoggerConfig myCodeConfig = new LoggerConfig("myCodeConfig", Level.DEBUG,false);
        myCodeConfig.addAppender(appender,Level.DEBUG,null);

        configuration.addAppender(appender);
        configuration.addLogger("myCodeConfig",myCodeConfig);

        loggerContext.updateLoggers();
    }


    public static void print(String m){
        org.apache.logging.log4j.core.LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(TestLog.class.getClassLoader(), false);
        Configuration configuration = loggerContext.getConfiguration();

        LoggerConfig myLoggerConfig = configuration.getLoggers().get("myCodeConfig");
        ReusableSimpleMessage message = new ReusableSimpleMessage();
        message.set(m);
        //第一个参数只是标识名，第二个参数，是定位堆栈，即打印类的
        myLoggerConfig.log(JavaConfig.class.getName(),"com.vic.log.codeconfig.JavaConfig",null, Level.INFO,message,null);
    }
}
