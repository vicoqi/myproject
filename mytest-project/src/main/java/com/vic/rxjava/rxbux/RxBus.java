package com.vic.rxjava.rxbux;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件总线
* https://www.jianshu.com/p/6c6ca95ec27b
 * 用 RxJava 打造 EventBus
 *
* @Author:wangqipeng
* @Date:14:58 2020-04-10
*/
@Slf4j
public class RxBus {
    private static volatile RxBus rxBus;

    private final Subject<Object> subject = PublishSubject.create().toSerialized();

    public static RxBus getRxBus() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if(rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    //Flowable 相当于 observable ,但是 Flowable 有被压，在rxjava2.0 中
    public   <T> Flowable<T> getObservable(Class<T> type){
        return subject.toFlowable(BackpressureStrategy.BUFFER).ofType(type);
    }

    public void post(Object o){
        subject.onNext(o);
    }

    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error){
        return getObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(next,error);
    }

    //订阅关系保存
    Map<String, CompositeDisposable> disposableMap = new HashMap<>();

    public void addSubscription(Object o, Disposable disposable) {
        String key = o.getClass().getName();
        log.info("addSubscription|{}",key);
        if (disposableMap.get(key) != null) {
            disposableMap.get(key).add(disposable);
        } else {
            CompositeDisposable disposables = new CompositeDisposable();
            disposables.add(disposable);
            disposableMap.put(key, disposables);
        }
    }

    public void unSubscribe(Object o) {
        String key = o.getClass().getName();
        log.info("unSubscribe|{}",key);
        if (!disposableMap.containsKey(key)) {
            return;
        }
        if (disposableMap.get(key) != null) {
            disposableMap.get(key).dispose();
        }
        disposableMap.remove(key);
    }

}
