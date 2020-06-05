package com.vic.proxy.jdk.impl;

import com.vic.proxy.jdk.interfaces.IHello;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-05-31 16:32
 **/
public class HelloServiceImpl implements IHello {
    @Override
    public String sayHello(String str) {
        return str;
    }
}
