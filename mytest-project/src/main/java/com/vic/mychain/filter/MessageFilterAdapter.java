package com.vic.mychain.filter;

import com.vic.mychain.message.IMessage;

public class MessageFilterAdapter<P extends IMessage> implements MessageFilter<P> {

    public void request(NextFilter<P> nextFilter, P request) throws Exception {
         nextFilter.request(request);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}

