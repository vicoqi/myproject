package com.vic.rxjava;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoPublishSubject {

    @Getter@Setter
    private Disposable disposable;

    public static void main(String[] args) {
        DemoPublishSubject demo = new DemoPublishSubject();
//        demo.test1();
//        demo.test2();
        demo.test4();
    }

    public void test1() {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.onNext(1);
        Disposable disposable = subject.subscribe(System.out::println);
            subject.onNext(2);
            subject.onNext(3);
        disposable.dispose();
            subject.onNext(4);
            subject.onComplete();

    }

    public void test2() {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.onNext(1);
        subject.subscribe(getObserver(this));
        subject.onNext(2);
        subject.onNext(3);
        this.getDisposable().dispose();
        subject.onNext(4);
        subject.onComplete();

    }

    //可以有多个订阅
    public void test3() {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.onNext(1);
        subject.observeOn(Schedulers.newThread()).subscribe(getObserver(this));
        subject.onNext(2);
        subject.onNext(3);
        Disposable disp = this.getDisposable();
        subject.onNext(4);
        subject.subscribe(getObserver(this));
        subject.onNext(5);
        subject.onNext(6);
        subject.onComplete();
    }
    //报错    #toSerialized() 都没有用
    public void test4() {
        PublishSubject<Integer> subject = PublishSubject.create();
//        Subject<Object> subject = PublishSubject.create().toSerialized();
        subject.onNext(1);
        subject.observeOn(Schedulers.newThread()).subscribe(getObserver(this));
        subject.onNext(2);
        subject.onNext(3);
        Disposable disp = this.getDisposable();
        subject.onNext(4);
        subject.subscribe(getObserver(this));
        subject.onNext(5);
        subject.onError(new NullPointerException("a is null"));
//        subject.onNext(1/0);
        subject.onNext(7);
        subject.onNext(8);
        subject.onComplete();

    }

    public <T> Observer<T> getObserver(DemoPublishSubject demoPublishSubject){
        return new Observer<T>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                log.info("Observer establish connect");
                demoPublishSubject.setDisposable(disposable);
            }

            @Override
            public void onNext(T t) {
                log.info("Observer receive:{}",t);
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("Observer receive error",throwable);
            }

            @Override
            public void onComplete() {
                log.info("Observer receive complete");
            }
        };
    }
}
