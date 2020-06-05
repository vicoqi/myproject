package com.vic.proxy;

import com.vic.proxy.cglib.HelloConcrete;
import com.vic.proxy.cglib.MyMethodInterceptor;
import com.vic.proxy.jdk.HelloInvocationHandler;
import com.vic.proxy.jdk.impl.HelloServiceImpl;
import com.vic.proxy.jdk.interfaces.IHello;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-05-31 16:37
 **/
public class ProxyTest {
    @Test
    public void testJdk(){
        /**
         # Hello代理对象的类型信息
         class=class jdkproxy.$Proxy0
         superClass=class java.lang.reflect.Proxy
         interfaces:
         interface jdkproxy.Hello
         invocationHandler

         代理对象的类型是jdkproxy.$Proxy0，这是个动态生成的类型，类名是形如$ProxyN的形式；
         父类是java.lang.reflect.Proxy，所有的JDK动态代理都会继承这个类；
         同时实现了Hello接口，也就是我们接口列表中指定的那些接口
         */
        IHello helloInvocation = (IHello) Proxy
                .newProxyInstance(getClass().getClassLoader()
                        ,new Class<?>[]{IHello.class},new HelloInvocationHandler(new HelloServiceImpl()));
        String ret = helloInvocation.sayHello("aaa");
        System.out.println(ret);
    }

    @Test
    public void testCglib(){
        // 2. 然后在需要使用HelloConcrete的时候，通过CGLIB动态代理获取代理对象。
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloConcrete.class);
        enhancer.setCallback(new MyMethodInterceptor());

        HelloConcrete hello = (HelloConcrete)enhancer.create();
        System.out.println(hello.sayHello("I love you!"));
    }
}
