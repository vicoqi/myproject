package com.vic.rxjava;

/**
 * @Auther: wqp
 * @Date: 2019/2/23 16:45
 * @Description:
 */
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DemoBufferOperator {

    public static void main(String[] args) throws InterruptedException {
        Observable<Integer> observable 	= Observable.range(10, 9);

        Observer<List<Integer>> observer = new Observer<List<Integer>>() {

            public void onComplete() {
                System.out.println("On Complete called");
            }

            public void onError(Throwable t) {
                t.printStackTrace();
            }

            public void onNext(List<Integer> value) {
                System.out.println("On Next called " + value +"\n");
            }

            public void onSubscribe(Disposable disposable) {
                System.out.println("On onSubscribe called " + disposable.isDisposed());
            }
        };

        Disposable disposable = observable.buffer(2).subscribe(item -> System.out.println("Emitted " + item + " items"));
        Thread.sleep(1000);
        observable.buffer(2).subscribe(observer);
    }
}
