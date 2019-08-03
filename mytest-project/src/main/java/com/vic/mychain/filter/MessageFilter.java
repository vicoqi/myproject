package com.vic.mychain.filter;

import com.vic.mychain.message.IMessage;

public interface MessageFilter<P,R> {

    void request(NextFilter nextFilter, IMessage request) throws Exception;

    public interface NextFilter {
        void request(IMessage request) throws Exception;
    }
}
