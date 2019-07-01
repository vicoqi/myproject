package com.vic.mychain.filter;

import com.vic.mychain.message.IMessage;

public interface MessageFilter<P extends IMessage, R> {

    R request(AGVContext context, NextFilter<P, R> nextFilter, P request) throws Exception;

    public interface NextFilter<P extends IMessage, R> {
        R request(AGVContext context, P request) throws Exception;
    }
}
