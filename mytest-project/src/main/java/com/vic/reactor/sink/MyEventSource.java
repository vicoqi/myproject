package com.vic.reactor.sink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发出事件，通知注册者
 */
public class MyEventSource {

    private List<MyEventListener> listeners;

    public MyEventSource() {
        this.listeners = new ArrayList<>();
    }

    public void register(MyEventListener listener) {    // 1
        listeners.add(listener);
    }

    public void newEvent(MyEvent event) {
        for (MyEventListener listener :
                listeners) {
            listener.onNewEvent(event);     // 2
        }
    }

    public void eventStopped() {
        for (MyEventListener listener :
                listeners) {
            listener.onEventStopped();      // 3
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyEvent {   // 4
        private Date timeStemp;
        private String message;
    }
}