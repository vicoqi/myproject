package com.vic.redis.lettuce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;


/**
 * @Date: 2018/11/20 19:29
 * @Description:
 */
//@SpringBootApplication(scanBasePackageClasses = {
////        RcsDataConfig.class
//})
@SpringBootApplication
public class StartUp {
    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(StartUp.class);
        application.addListeners(new ApplicationListener<ApplicationReadyEvent>() {
            @Override
            public void onApplicationEvent(ApplicationReadyEvent event) {
                try {

//                    event.getApplicationContext().getBean(ITrafficOperation.class).handleAgvMoveEvent(agvMoveRequest,(re)->{
//                        System.out.print("推送结果");
//                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.print("[系统初始化失败]");
                    System.exit(0);
                }
            }
        });
        application.run(args);
    }
}
