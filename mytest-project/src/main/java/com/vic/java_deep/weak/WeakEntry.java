package com.vic.java_deep.weak;

import java.lang.ref.WeakReference;

/**
 * @author: wangqp
 * @create: 2020-07-15 11:11
 */
public class WeakEntry extends WeakReference<Object> {
    //WeakReference 有个带参的构造函数，子类一定要实现这个构造器
    public WeakEntry(Object referent) {
        super(referent);
    }
}
