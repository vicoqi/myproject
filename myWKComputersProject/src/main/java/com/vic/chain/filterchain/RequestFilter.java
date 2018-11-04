package com.vic.chain.filterchain;


import com.vic.chain.AGVContext;
import com.vic.chain.Request;

public interface RequestFilter<P extends Request, R> {

    R request(AGVContext context, NextFilter<P, R> nextFilter, P request) throws Exception;

    public interface NextFilter<P extends Request, R> {

        R request(AGVContext context, P request) throws Exception;
    }
}
