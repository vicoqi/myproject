package com.vic.rxjava.rxbux;

import lombok.extern.slf4j.Slf4j;

/**
* 初始化消息监听器
* @Author:wangqipeng
* @Date:15:25 2020-04-10
*/
@Slf4j
public class InitEventListener extends EventListener{
    private final Class eventType = String.class;

    //注册初始化监听器
    public void register(){
        super.addSubscribe(eventType,o->{
            log.info("excute ini message|{}",o);
        });
    }
}
