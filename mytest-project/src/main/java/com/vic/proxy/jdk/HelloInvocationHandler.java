package com.vic.proxy.jdk;

import com.vic.proxy.jdk.interfaces.IHello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-05-31 16:34
 **/
public class HelloInvocationHandler implements InvocationHandler {

    private IHello hello;
    public HelloInvocationHandler(IHello hello) {
        this.hello = hello;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("sayHello".equals(method.getName())) {
            System.out.println("is invocation");
        }
        return method.invoke(hello, args);
    }
}
