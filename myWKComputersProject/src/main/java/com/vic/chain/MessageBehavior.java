/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.vic.chain;


import com.vic.chain.filter.AGVMessageFirstFilter;
import com.vic.chain.filter.IdleAGVFilter;
import com.vic.chain.filter.PathRePlanFilter;
import com.vic.chain.filterchain.DefaultRequestFilterChainBuilder;
import com.vic.chain.filterchain.RequestHandler;
import com.vic.chain.handler.AGVMessageHandler;

import javax.annotation.Resource;


//@Service
public class MessageBehavior extends CommonBehavior {

    // handler是 request 经过 filterChain后 最终的处理者
    private RequestHandler handler;

    public MessageBehavior(){
        init();
    }

    @Override
    public void buildRequestChain(DefaultRequestFilterChainBuilder<AGVRequest, AgvResponse> builder) {
        PathRePlanFilter pathRePlanFilter = new PathRePlanFilter();
        AGVMessageFirstFilter agvMessageFirstFilter = new AGVMessageFirstFilter();
        IdleAGVFilter idleAGVFilter = new IdleAGVFilter();

        builder.addLast("AGVLocationFilter", agvMessageFirstFilter);
        builder.addLast("IdleAGVFilter", idleAGVFilter);
        builder.addLast("PathRePlanFilter", pathRePlanFilter);
    }

    @Override
    protected String chainType() {
        return "agv-message";
    }

    @Override
    public void setHandler(RequestHandler<AGVRequest, AgvResponse> handler) {
        this.handler = handler;
    }

    @Override
    public RequestHandler<AGVRequest, AgvResponse> getHandler() {
        return handler = new AGVMessageHandler();
    }
}
