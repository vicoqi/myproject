package com.vic.spring.imports;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Created by wang on 2019/6/28.
 */
public class Start {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        StudentBean studentBean = applicationContext.getBean(StudentBean.class);
        System.out.println(studentBean.getName());
    }
}
