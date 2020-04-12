package com.vic.rxjava.rxbux;

public class RxBusMain {
    public static void main(String[] args) throws InterruptedException {
        new InitEventListener().register();
        RxBus.getRxBus().post("i am chip");
        Thread.sleep(1000);
        RxBus.getRxBus().post("she is qinyuan");


        InitEventListener initEventListener = new InitEventListener();
        initEventListener.register();
        RxBus.getRxBus().post("i am chip");
        Thread.sleep(1000);
        RxBus.getRxBus().post("she is qinyuan");
//        initEventListener.destory();

        Thread.sleep(3000);
        InitEventListener initEventListener1 = new InitEventListener();
        initEventListener1.register();
        RxBus.getRxBus().post("they will be marry");
        initEventListener1.destory();

    }
}
