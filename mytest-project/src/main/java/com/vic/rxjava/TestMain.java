package com.vic.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import lombok.Getter;

/**
 * @Auther: wqp
 * @Date: 2019/2/22 16:03
 * @Description:
 */
public class TestMain {
    private Disposable mDisposable;
    @Getter
    private ObservableEmitter<String> em;
    public static void main(String[] args) {
        TestMain testMain = new TestMain();
        testMain.rxJavaChainUse();
//        testMain.getEm().onNext("连载1");
//        testMain.getEm().onNext("连载2");
//        testMain.getEm().onNext("连载3");
//        testMain.getEm().onComplete();
    }

    /**
     * onSubscribe（Disposable d）里面的Disposable对象要说一下，
     * Disposable英文意思是可随意使用的，这里就相当于读者和连载小说的订阅关系，
     * 如果读者不想再订阅该小说了，可以调用 mDisposable.dispose()取消订阅，此时连载小说更新的时候就不会再推送给读者了。
     */
    //RxJava基本使用
    public void rxJavaBaseUse(){
        //被观察者

//        PublishSubject subject = PublishSubject.create();
//        subject.onNext();
//
//        subject.doOnNext().sub
        Observable novel=Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("连载1");
            emitter.onNext("连载2");
            emitter.onNext("连载3");
            emitter.onComplete();
            System.out.println("emitter"+":::"+Thread.currentThread());
//            em = emitter;
        });
        //观察者
        Observer<String> reader=new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable=d;
//                Log.e(TAG,"onSubscribe");
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String value) {
                if ("2".equals(value)){
                    mDisposable.dispose();
                    return;
                }
//                Log.e(TAG,"onNext:"+value);
                System.out.println("onNext:"+value+":::"+Thread.currentThread());
            }

            @Override
            public void onError(Throwable e) {
//                Log.e(TAG,"onError="+e.getMessage());
                System.out.println("onError="+e.getMessage());
            }

            @Override
            public void onComplete() {
//                Log.e(TAG,"onComplete()");
                System.out.println("onComplete()");
            }
        };
        //订阅
        novel.subscribe(reader);
    }
    //难度2   RxJava链式使用

    /**
     * 前言里面有提到，RxJava是支持异步的，但是RxJava是如何做到的呢？
     * 这里就需要Scheduler。Scheduler，英文名调度器，它是RxJava用来控制线程。
     * 当我们没有设置的时候，RxJava遵循哪个线程产生就在哪个线程消费的原则，也就是说线程不会产生变化，始终在同一个。
     * 然后我们一般使用RxJava都是后台执行，前台调用，
     * 本着这个原则，我们需要调用observeOn(AndroidSchedulers.mainThread())，observeOn是事件回调的线程，
     * AndroidSchedulers.mainThread()一看就知道是主线程，
     * subscribeOn(Schedulers.io())，subscribeOn是事件执行的线程，Schedulers.io()是子线程，
     * 这里也可以用Schedulers.newThread()，只不过io线程可以重用空闲的线程，
     * 因此多数情况下 io() 比 newThread() 更有效率。前面的代码根据异步和链式编程的原则，我们可以写成如下。
     * https://www.jianshu.com/p/cd3557b1a474
     */

    //            .subscribeOn(new NewThreadScheduler())   //指定事件产生的线程,Observable的方法所在的线程
//                .observeOn(new NewThreadScheduler());   //指定事件消费的线程,即Subscriber所运行在的线程
    private void rxJavaChainUse(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                System.out.println("emitter"+":::"+Thread.currentThread().getName());
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                //io 耗时操作
                emitter.onComplete();
            }
        }).subscribeOn(new NewThreadScheduler())  //执行在io线程
          .observeOn(new NewThreadScheduler())   //回调在主线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe"+":::"+Thread.currentThread().getName());
                    }
                    @Override
                    public void onNext(String value) {
                        System.out.println("onNext:"+value+":::"+Thread.currentThread().getName());
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError="+e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        System.out.println("onComplete()"+":::"+Thread.currentThread());
                    }
                });
    }
}
