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

    public static void main(String[] args) {
        Observable<Integer> observable 	= Observable.range(10, 9);

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

        observable.buffer(2).subscribe(item -> System.out.println("Emitted " + item + " items"));

        observable.buffer(2).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> list) throws Exception {
                System.out.println("Processing items : " + list);
                java.util.function.Consumer<Integer> consumer = (Integer i) -> System.out.println("accept value " + i.intValue());
                list.forEach(consumer);
				/*
				int total = 0;
				list.forEach(new java.util.function.Consumer<Integer>(){
					@Override
					public void accept(Integer t) {
						total = total + t.intValue();
						System.out.println("total " + total);
					}
				});
				*/
            }
        });
    }
}
