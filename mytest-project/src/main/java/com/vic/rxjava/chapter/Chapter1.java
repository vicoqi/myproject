package com.vic.rxjava.chapter;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
*
* @Author:wangqipeng
* @Date:20:14 2020-04-12
*/
public class Chapter1 {
    public static void main(String[] args) throws InterruptedException {
        //被观察者
        Observable<Integer> observable=Observable.create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        });

        Observer<Integer> observer = new Observer<Integer>() {

            public void onComplete() {
                System.out.println("On Complete called");
            }

            public void onError(Throwable t) {
                t.printStackTrace();
            }

            public void onNext(Integer value) {
                System.out.println("On Next called " + value +"\n");
            }

            public void onSubscribe(Disposable disposable) {
                System.out.println("On onSubscribe called " + disposable.isDisposed());
            }
        };

        observable.subscribe(observer);
        //看是否后订阅者，少接受到事件
        Thread.sleep(1000);
        observable.buffer(2).subscribe(item -> System.out.println("Emitted " + item + " items"));

    }
}
