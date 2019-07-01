package com.vic.mychain.filter;

import com.vic.mychain.message.IMessage;

public interface MessageFilter<P extends IMessage> {

    void request(NextFilter<P> nextFilter, P request) throws Exception;

    public interface NextFilter<P extends IMessage> {
        void request(P request) throws Exception;
    }
}
