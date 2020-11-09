package com.vic.rxjava.optest;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Subject：它既是Observable，又是observer。也就是既可以发送事件，也可以接收事件。
 *
 * 下面是四个子类
 * PublishSubject、   //PublicSubject：接收到订阅之后的所有数据。
 * ReplaySubject、    //ReplaySubject：接收到所有的数据，包括订阅之前的所有数据和订阅之后的所有数据。
 * BehaviorSubject、  //BehaviorSubject：接收到订阅前的最后一条数据和订阅后的所有数据。
 * AsyncSubject的区别：//AsyncSubject：不管在什么位置订阅，都只接接收到最后一条数据
 */
@Slf4j
public class SubjectTest {

    @Getter@Setter
    private Disposable disposable;

    public static void main(String[] args) {
        SubjectTest demo = new SubjectTest();
//        demo.test1();
//        demo.test2();
        demo.test4();
    }
    @Test
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

    @Test
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
    @Test
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
    @Test
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

    public <T> Observer<T> getObserver(SubjectTest demoPublishSubject){
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
