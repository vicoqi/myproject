/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.vic.mychain.start;

import com.vic.mychain.chain.DefaultRequestFilterChainBuilder;
import com.vic.mychain.implfilter.FirstFilter;
import com.vic.mychain.implfilter.SecondFilter;

//@Service
public class MessageBehavior extends CommonBehavior {

    // handler是 request 经过 filterChain后 最终的处理者
//    private RequestHandler handler;

    public MessageBehavior(){
        init();
    }

    @Override
    public void buildRequestChain(DefaultRequestFilterChainBuilder builder) {
        FirstFilter firstFilter = new FirstFilter();
        SecondFilter secondFilter = new SecondFilter();
//        IdleAGVFilter idleAGVFilter = new IdleAGVFilter();
        builder.addLast("FirstFilter", firstFilter);
//        builder.addLast("IdleAGVFilter", idleAGVFilter);
        builder.addLast("SecondFilter", secondFilter);
    }

    @Override
    protected String chainType() {
        return "agv-message";
    }

//    public void setHandler(RequestHandler<AGVRequest, AgvResponse> handler) {
//        this.handler = handler;
//    }

//    public RequestHandler<AGVRequest, AgvResponse> getHandler() {
//        return handler = new AGVMessageHandler();
//    }
}
