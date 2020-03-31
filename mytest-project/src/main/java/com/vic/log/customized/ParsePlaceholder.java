package com.vic.log.customized;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

/**
* log4j2
 * 自定解析 xml 中的占位符
 * 例：
 * <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
* @Author:wangqipeng
* @Date:15:46 2020-03-18
*/
@Plugin(name = "ParsePlaceholderConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "wqp"})
@PerformanceSensitive("allocation")
public class ParsePlaceholder extends LogEventPatternConverter {


    private ParsePlaceholder(final String[] options) {
        super("WQP", "wqp");
    }

    public static ParsePlaceholder newInstance(final String[] options) {
        return new ParsePlaceholder(options);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        toAppendTo.append("wqp_hello");
    }
}
