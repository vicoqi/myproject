package com.vic.spring.annotationProcessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@SpringBootApplication
public class StartProcessor {
    public static void main(String[] args) {
//        SpringApplication application = new SpringApplication(StartProcessor.class);
//        application.run(args);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(StartConfig.class);
        MyTest bean = applicationContext.getBean(MyTest.class);
        System.out.println(bean);
    }
}
