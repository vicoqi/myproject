package com.vic.rxjava.optest;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import org.junit.Test;

/**
 * @author: wangqp
 * @create: 2020-10-15 20:02
 */
/**
 * 因此Rxjava2.0版本提供了 Flowable解决背压问题。
 *
 */
public class FlowableTest {

    @Test
    public void test(){
        Flowable flowable = Flowable.create((FlowableOnSubscribe<Integer>) e -> {
            for(int i =0 ; i<10;i++){
                e.onNext(i);
            }
        }, BackpressureStrategy.BUFFER);


    }
}
/**
 *仔细看一下上述案例中Flowable 的具体使用方法，里面有两个地方和我们之前Rxjava的使用经验不同，
 * 一个是创建Flowable 需要传入一个 BackpressureStrategy的参数，
 * 另外是在 Subscriber 的onSubscribe方法中调用 subscription.request 方法。
 * 这两处是做什么用的呢。回到开头我们提出的问题，为了解决上游发送事件和下游处理事件速度不一致的问题，
 * 我们需要一个策略能让下游告诉上游，下游的处理能力是怎样的，这两处不同就是为了解决这个问题。
 *
 * 首先我们解释下 BackpressureStrategy参数的含义，我们这先介绍下概念，详细理解后续会继续介绍
 * BackpressureStrategy.ERROR：若上游发送事件速度超出下游处理事件能力，且事件缓存池已满，则抛出异常
 * BackpressureStrategy.BUFFER：若上游发送事件速度超出下游处理能力，则把事件存储起来等待下游处理
 * BackpressureStrategy.DROP：若上游发送事件速度超出下游处理能力，事件缓存池满了后将之后发送的事件丢弃
 * BackpressureStrategy.LATEST：若上有发送时间速度超出下游处理能力，则只存储最新的128个事件
 * 备注：128是Flowable中事件缓存池的大小
 *
 * 那么这个subscripiton.request方法主要用来做什么呢，这个方法是下游用来告知上游处理事件的能力的。
 * Flowable采用了一种响应式拉取的思路用来解决上下游处理速度不统一的问题。
 * 简而言之，所谓响应式拉取就是，下游向上游告知自己的处理能力，下游处理完一个事件，然后告诉上游，
 * 上游则继续发送事件，继续等待下游的通知。 当调用subscription.request(1)时，就是下游向上游说：我现在能处理一个事件。
 *
 */
