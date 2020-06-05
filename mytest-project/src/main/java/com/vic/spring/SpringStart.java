package com.vic.spring;

import com.vic.redis.lettuce.StartUp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.Properties;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-05-14 17:19
 **/
@SpringBootApplication(exclude ={RedisAutoConfiguration.class,} ,scanBasePackages = "com.vic.spring" )
public class SpringStart {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringStart.class);
        Properties defaultProperties = new Properties();
        application.setDefaultProperties(defaultProperties);
        application.addListeners((ApplicationListener<ApplicationReadyEvent>) event -> {
            try {
                System.out.print("[系统启动]");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("[系统初始化失败]");
                System.exit(0);
            }
        });
        application.run(args);
    }
}
