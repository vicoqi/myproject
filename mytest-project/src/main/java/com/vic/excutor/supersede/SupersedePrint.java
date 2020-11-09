package com.vic.excutor.supersede;

import java.io.IOException;

/**
 * 交替打印,打印 1 ~ 100
 * notify  wait
 * @author: wangqp
 * @create: 2020-11-09 10:52
 */
public class SupersedePrint {

    private Object object = new Object();
    /**
     * 貌似可以加 volatile 也可以不
     */
    private int i = 0;


    public static void main(String[] args) throws IOException {
        SupersedePrint supersedePrint = new SupersedePrint();
        new Thread(() -> {
            while (supersedePrint.i <= 100){
                synchronized (supersedePrint.object){
                    if (supersedePrint.i%2 == 0){
                        System.out.println(supersedePrint.i+"i am thread A");
                        supersedePrint.i++;
                        supersedePrint.object.notify();
                    }else {
                        try {
                            supersedePrint.object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(() -> {
            while (supersedePrint.i <= 100){
                synchronized (supersedePrint.object){
                    if (supersedePrint.i%2 != 0){
                        System.out.println(supersedePrint.i+"i am thread B");
                        supersedePrint.i++;
                        supersedePrint.object.notify();
                    }else {
                        try {
                            supersedePrint.object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        System.in.read();

    }

}
