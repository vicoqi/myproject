package com.vic.log.codeconfig;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.*;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;

/**
 * @program: myproject
 * @description: 从网上copy 的log4j 的java 代码配置
 * @author: wangqp
 * @create: 2020-05-06 16:22
 **/
public class Example {
//    /**
//     * 日志打印的目录
//     */
//    private static final String datalogDir = "logs" + File.separator + "business";
//
//    private static final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
//    private static final Configuration config = ctx.getConfiguration();
//    private static final String INFO = "info";
//    private static final String ERROR = "error";
//    /**
//     * 启动一个动态的logger
//     */
//    private static void start(String loggerName) {
//
//        //创建一个展示的样式：PatternLayout，   还有其他的日志打印样式。
//        Layout layout = PatternLayout.newBuilder()
//                .withConfiguration(config).withPattern("%d{yyyy-MM-dd HH:mm:ss,SSS} %t %-5p %C %c{1}:%L -%m%n").build();
//
//        //单个日志文件大小
//        TimeBasedTriggeringPolicy tbtp = TimeBasedTriggeringPolicy.createPolicy(null, null);
//        TriggeringPolicy tp = SizeBasedTriggeringPolicy.createPolicy("100 MB");// 大小分割
//        CompositeTriggeringPolicy policyComposite = CompositeTriggeringPolicy.createPolicy(tbtp, tp);
//
//        String loggerDir = datalogDir + File.separator + loggerName;
//        //删除日志的条件
//       /* IfFileName ifFileName = IfFileName.createNameCondition(null, loggerName + "\\.\\d{4}-\\d{2}-\\d{2}.*");
//        IfLastModified ifLastModified = IfLastModified.createAgeCondition(Duration.parse("1d"));
//        DeleteAction deleteAction = DeleteAction.createDeleteAction(
//                loggerDir, false, 1, false, null,
//                new PathCondition[]{ifLastModified, ifFileName}, null, config);
//        Action[] actions = new Action[]{deleteAction};*/
//
//        DefaultRolloverStrategy strategy = DefaultRolloverStrategy.createStrategy(
//                "100000", "1", null, null, null, false, config);
//        ThresholdFilter filter = ThresholdFilter.createFilter(Level.INFO, Filter.Result.ACCEPT, Filter.Result.DENY);
//        ThresholdFilter denyWarnFilter = ThresholdFilter.createFilter(Level.WARN, Filter.Result.DENY, Filter.Result.NEUTRAL);
//        RollingFileAppender appender = RollingFileAppender.newBuilder()
//                .withFilter(filter)
//                .withFilter(denyWarnFilter)
//                .withFileName(loggerDir + "-info.log")
//                .withFilePattern(datalogDir + File.separator + "${date:yyyy-MM}" + File.separator + "info-%d{yyyy-MM-dd}-" + loggerName + ".%i.log")
//                .withAppend(true)
//                .withStrategy(strategy)
//                .withName(loggerName + INFO)
//                .withPolicy(policyComposite)
//                .withLayout(layout)
//                .withConfiguration(config)
//                .build();
//        appender.start();
//        config.addAppender(appender);
//
//        ThresholdFilter filterError = ThresholdFilter.createFilter(Level.ERROR, Filter.Result.ACCEPT, Filter.Result.DENY);
//        RollingFileAppender appenderError = RollingFileAppender.newBuilder()
//                .withFilter(filterError)
//                .withFileName(loggerDir + "-error.log")
//                .withFilePattern(datalogDir + File.separator + "${date:yyyy-MM}" + File.separator + "error-%d{yyyy-MM-dd}-" + loggerName + ".%i.log")
//                .withAppend(true)
//                .withStrategy(strategy)
//                .withName(loggerName + ERROR)
//                .withPolicy(policyComposite)
//                .withLayout(layout)
//                .withConfiguration(config)
//                .build();
//        appenderError.start();
//        config.addAppender(appenderError);
//
//        AppenderRef refInfo = AppenderRef.createAppenderRef(loggerName + INFO, null, null);
//        AppenderRef refError = AppenderRef.createAppenderRef(loggerName + ERROR, null, null);
//        AppenderRef[] refs = new AppenderRef[]{refInfo, refError};
//
//        LoggerConfig loggerConfig = LoggerConfig.createLogger(false,
//                Level.INFO, loggerName, "true", refs, null, config, null);
//
//        loggerConfig.addAppender(appender, Level.INFO, null);
//        loggerConfig.addAppender(appenderError, Level.ERROR, null);
//        config.addLogger(loggerName, loggerConfig);
//        ctx.updateLoggers();
//    }
}
