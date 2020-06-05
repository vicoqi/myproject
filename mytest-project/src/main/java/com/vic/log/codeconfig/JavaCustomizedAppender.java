package com.vic.log.codeconfig;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.Message;

import java.io.Serializable;

/**
* xml中需要配置  name 标签
* @Author:wangqipeng
* @Date:19:17 2020-04-13
*/
//@Plugin(name = "JavaCustomized", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class JavaCustomizedAppender extends AbstractAppender {


    private JavaCustomizedAppender(String name, Layout<? extends Serializable> layout, Filter filter, boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }


    @PluginBuilderFactory
    public static <B extends JavaCustomizedAppender.Builder<B>> B newBuilder() {
        return new Builder<B>().asBuilder();
    }


    public static class Builder<B extends Builder<B>> extends AbstractAppender.Builder<B>
            implements org.apache.logging.log4j.core.util.Builder<JavaCustomizedAppender> {
        @Override
        public JavaCustomizedAppender build() {
            return new JavaCustomizedAppender(getName(), getMyLayout(), getFilter(), isIgnoreExceptions());
        }
    }

    @Override
    public void append(LogEvent event) {
        Message mess = event.getMessage();
        mess.getFormattedMessage();
        mess.getFormat();
        mess.getParameters();
        System.out.println(event.getLoggerFqcn());
        System.out.println(event.getLoggerName());
        System.out.println(event.getSource());
        System.out.println(event.getContextStack());
        System.out.println(event.getThrown());

    }

    private static Layout<? extends Serializable> getMyLayout(){
        Layout layout = PatternLayout.newBuilder().withPattern("%d{yyyy-MM-dd HH:mm:ss,SSS} %t %-5p %C %c{1}:%L -%m%n").build();
        return layout;
    }
}
