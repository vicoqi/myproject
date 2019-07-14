package com.vic.mychain.filterchain;


import com.vic.mychain.domain.ChainContext;
import com.vic.mychain.domain.Request;

public interface RequestFilter<P extends Request, R> {

    R request(ChainContext context, NextFilter<P, R> nextFilter, P request) throws Exception;

    interface NextFilter<P extends Request, R> {
        R request(ChainContext context, P request) throws Exception;
    }
}
