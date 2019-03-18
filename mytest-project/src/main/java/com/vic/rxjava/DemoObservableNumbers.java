package com.vic.rxjava;

/**
 * @Auther: wqp
 * @Date: 2019/2/23 16:40
 * @Description:
 */
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class DemoObservableNumbers {

    public static void main(String[] args) {
        Integer[] nums = {1,23,34,21,33, 4, 54};
        List<Integer> numList = Arrays.asList(nums);


        Observable.just(numList).subscribe(item -> System.out.println(item));
        //From the test below, it is evident the just() method only emits one Observerable
        Observable.just(numList).subscribe(item -> System.out.println(item + "test"));

        Observable<List<Integer>> observable = Observable.just(numList);
        observable.subscribe(item -> System.out.println(item));

    }

}
