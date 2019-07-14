package com.vic.redis.lettuce;

//import com.kc.evo.rcs.data.biz.RcsDataConfig;

import com.kc.evo.rcs.traffic.core.manager.cfg.CollisionConfig;
import com.kc.evo.rcs.traffic.manager.api.operation.interfaces.ITrafficOperation;
import com.kc.evo.rcs.traffic.manager.api.operation.request.AGVMoveRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

//import com.kc.evo.rcs.traffic.cache.cfg.CacheConfig;

/**
 * @Date: 2018/11/20 19:29
 * @Description:
 */
@SpringBootApplication(scanBasePackageClasses = {
        CollisionConfig.class,
        //CacheConfig.class,
//        RcsDataConfig.class
})
public class StartUp {

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(StartUp.class);
        application.addListeners(new ApplicationListener<ApplicationReadyEvent>() {
            @Override
            public void onApplicationEvent(ApplicationReadyEvent event) {
                try {
                    AGVMoveRequest agvMoveRequest = new AGVMoveRequest();
                    agvMoveRequest.setAgvId("666");
                    agvMoveRequest.setCurPos("917523");
                    agvMoveRequest.setDestPos("917528");
                    agvMoveRequest.setStartPos("917523");
                    agvMoveRequest.setWithBucket(false);
                    agvMoveRequest.setJobSn(1234);
                    event.getApplicationContext().getBean(ITrafficOperation.class).handleAgvMoveEvent(agvMoveRequest,(re)->{
                        System.out.print("推送结果");
                    });
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
