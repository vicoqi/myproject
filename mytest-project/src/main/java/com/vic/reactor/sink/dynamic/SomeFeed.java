package com.vic.reactor.sink.dynamic;

import java.util.LinkedList;
import java.util.List;

public class SomeFeed<T> {
    private List<SomeListener> listeners = new LinkedList<>();

    public void register(SomeListener listener) {
        listeners.add(listener);
    }
}
