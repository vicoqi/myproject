package com.vic.chain.filterchain;

import com.vic.chain.AGVContext;
import com.vic.chain.Request;

public class RequestFilterAdapter<P extends Request, R> implements RequestFilter<P, R> {

    public R request(AGVContext context, NextFilter<P, R> nextFilter, P request) throws Exception {
        return nextFilter.request(context, request);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}

