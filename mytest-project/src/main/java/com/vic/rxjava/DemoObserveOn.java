package com.vic.rxjava;

/**
 * @Auther: wqp
 * @Date: 2019/2/23 15:49
 * @Description:
 * //有订阅者注册的时候 回调这个fromCallable 产生数据
 * 每次有订阅者触发一次，并Emitted.completed()
 */
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class DemoObserveOn {

    public static void main(String args[]) {

        //被观察者
        Observable<Integer> observable = Observable.fromCallable(new Callable() {

            @Override
            public Integer call() throws Exception {
                System.out.println("call:Thread is -" + Thread.currentThread().getName());

                Thread.sleep(1000);
                System.out.println("call sleep:Thread is -" + Thread.currentThread().getName());
                return 5;
            }
        });

        //订阅者
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                // TODO Auto-generated method stub
                System.out.println("onSubscribe:Thread is -" + Thread.currentThread().getName());
            }

            @Override
            public void onNext(Integer t) {
                System.out.println("onNext:Subscriber Thread is -" + Thread.currentThread().getName());
                System.out.println("onNext:Emitted item " + t);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Sequence completed successfully");
            }
        };

        observable.subscribe(observer);

        try {
            Thread.sleep(1000);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("******** start second observer");
        observable.subscribe(observer);

        System.out.println("******** start three observer");
        //订阅者 会在 observeOn()线程中执行
        observable.observeOn(Schedulers.newThread()).subscribe(observer);
    }

}
