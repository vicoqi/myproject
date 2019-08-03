package com.vic.mychain.filter;

import com.vic.mychain.message.IMessage;

public class MessageFilterAdapter<P,R> implements MessageFilter<P,R> {

    public void request(NextFilter nextFilter, IMessage request) throws Exception {
         nextFilter.request(request);
    }

    public Class getNextClass(R  nexRequest) {
        return nexRequest.getClass();
    }
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

