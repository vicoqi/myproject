package com.vic.spring.imports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Created by wang on 2019/6/28.
 */
@SpringBootApplication
public class Start {
//    public static void main(String[] args) throws IOException {
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
//        StudentBean studentBean = applicationContext.getBean(StudentBean.class);
//        System.out.println(studentBean.getName());
//    }
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Start.class);
        application.run(args);
    }
}
