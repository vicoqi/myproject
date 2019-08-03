package com.vic.spring.annotationProcessor;

import org.springframework.stereotype.Component;

@Component
public class MyTest extends Test{
    @MyEventListener
    public void see(EventObject eventObject){

    }
}
