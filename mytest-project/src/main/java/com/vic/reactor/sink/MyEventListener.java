package com.vic.reactor.sink;


/**
 * 监听事件
 */
public interface MyEventListener {
    void onNewEvent(MyEventSource.MyEvent event);
    void onEventStopped();
}
