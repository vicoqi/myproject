package com.vic.mychain.filter;

import com.vic.mychain.message.IMessage;

public class MessageFilterAdapter<P extends IMessage, R> implements MessageFilter<P, R> {

    public R request(AGVContext context, NextFilter<P, R> nextFilter, P request) throws Exception {
        return nextFilter.request(context, request);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}

