package com.vic.rxjava;

/**
 * @Auther: wqp
 * @Date: 2019/2/23 16:27
 * @Description:
 */
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class DemoRxOperators {

    public static void main(String args[]) {

        Observable<Integer> observable = Observable.range(1, 5);

        System.out.println("----- Without Operator -----");
        observable.subscribe(item -> System.out.println("Emitted " + item + " items"));

        System.out.println("----- With FlatMap Operator -----");
        //flatMap 一个消息分裂为多个消息，然后合并为一个多个消息的序列
        observable.flatMap(item -> Observable.range(item, 3)).subscribe(item -> System.out.println("Emitted " + item + " items"));

        System.out.println("----- With only Map Operator -----");
        Function<Integer,Integer> mapInteger = (a) -> { return a.intValue() + 10;	};

        observable.map(mapInteger).subscribe(item -> System.out.println("Emitted " + item + " items"));

        System.out.println("----- With Filter Operator -----");
        Predicate<Integer> condition = (a) -> {return a > 11; };
        observable.map(mapInteger).filter(condition).subscribe(item -> System.out.println("Emitted " + item + " items"));

    }
}
