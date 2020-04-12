package com.vic.rxjava.rxbux;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.extern.slf4j.Slf4j;

/**
* 监听器的父类
* @Author:wangqipeng
* @Date:16:03 2020-04-10
*/
@Slf4j
public abstract class EventListener {
    protected <M> void addSubscribe(Class<M> eventType, Consumer<M> action) {
        Disposable disposable = RxBus.getRxBus().doSubscribe(eventType, action, throwable -> log.error("",throwable));
        RxBus.getRxBus().addSubscription(this, disposable);
    }

    protected <M> void addSubscribe(Class<M> eventType, Consumer<M> action, Consumer<Throwable> error) {
        Disposable disposable = RxBus.getRxBus().doSubscribe(eventType, action, error);
        RxBus.getRxBus().addSubscription(this, disposable);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        RxBus.getRxBus().unSubscribe(this);
    }

    protected void destory(){
        log.info("destory|{}",this.getClass().getName());
        RxBus.getRxBus().unSubscribe(this);
    }
}

