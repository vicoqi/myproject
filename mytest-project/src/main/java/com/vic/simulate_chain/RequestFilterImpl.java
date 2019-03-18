package com.vic.simulate_chain;

import com.vic.chain.AGVContext;
import com.vic.chain.Request;

/**
 * @Auther: wqp
 * @Date: 2018/12/19 16:29
 * @Description:
 */
public class RequestFilterImpl implements RequestFilter{
    @Override
    public Object request(AGVContext context, NextFilter nextFilter, Request request) throws Exception {
        return null;
    }
}
