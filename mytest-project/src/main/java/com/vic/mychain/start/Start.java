package com.vic.mychain.start;


/**
 * Created by wang on 2019/7/3.
 */
public class Start {
    public static void main(String[] args) {
        MessageBehavior messageBehavior = new MessageBehavior();
        messageBehavior.init();
        messageBehavior.execute(new StartMessage());
    }
}
