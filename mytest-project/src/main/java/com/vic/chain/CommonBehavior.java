/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.vic.chain;

import com.vic.chain.filterchain.DefaultRequestFilterChain;
import com.vic.chain.filterchain.DefaultRequestFilterChainBuilder;
import com.vic.chain.filterchain.RequestBehavior;
import com.vic.chain.filterchain.RequestFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;


public abstract class CommonBehavior implements RequestBehavior<AGVRequest, AgvResponse> {

    private static final Logger logger = LoggerFactory.getLogger(CommonBehavior.class);
    private final RequestFilterChain<AGVRequest, AgvResponse> filterChain =
        new DefaultRequestFilterChain<>(this);

    @PostConstruct
    public void init() {
        DefaultRequestFilterChainBuilder<AGVRequest, AgvResponse> builder =
            new DefaultRequestFilterChainBuilder<AGVRequest, AgvResponse>();
        try {
            this.buildRequestChain(builder);
            builder.buildFilterChain(filterChain);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public AgvResponse execute(AGVContext context, AGVRequest request) {
        try {
            long start = System.currentTimeMillis();
            AgvResponse response = filterChain.fireRequest(context, request);

            return response;
        } catch (Exception e) {
            logger.info("aa");
            e.printStackTrace();
            return new AgvResponse();
        }
    }



    protected abstract void buildRequestChain(DefaultRequestFilterChainBuilder<AGVRequest, AgvResponse> builder);

    protected abstract String chainType();


}
