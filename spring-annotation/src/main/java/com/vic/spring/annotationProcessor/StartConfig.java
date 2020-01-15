package com.vic.spring.annotationProcessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@ComponentScan("com.vic.spring.annotationProcessor")
@EnableAsync
public class StartConfig {
    private long rejectHandleCount;
    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(4);
        executor.setKeepAliveSeconds(20);
        executor.setThreadNamePrefix("traffic-");
        executor.setRejectedExecutionHandler((r, executor1) -> {
            System.out.println("rejectedExecution");
            rejectHandleCount++;
            if (rejectHandleCount % 1024 == 0) {
//                log.error("rejected count:{}",rejectHandleCount);
            }
        });
        executor.initialize();
        return executor;
    }
}
