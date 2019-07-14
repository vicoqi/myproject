package com.vic.annotate;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class ApplicationStart {
    public static void main(String[] args) {
        System.out.println(HandlerType.class.getPackage().getName());
        SpringApplication application = new SpringApplication(ApplicationStart.class);
        application.addListeners((ApplicationListener<ApplicationReadyEvent>) event -> {
            try {
                //event.getApplicationContext().getBean(CollisionService.class).init();
                //event.getApplicationContext().getBean(AgvMsgBusConfig.class).init();
                System.out.println("aaaa");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        });
        application.run(args);
    }
}
