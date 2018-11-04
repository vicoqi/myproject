package com.vic.chain.filter;

import com.vic.chain.AGVContext;
import com.vic.chain.AGVRequest;
import com.vic.chain.AgvResponse;
import com.vic.chain.filterchain.RequestFilterAdapter;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by happy on 2018/10/19.
 *
 * IDLE状态AGV，添加一个deadlockMoveJob到一个最近无AGV的STORAGE，
 *
*/
// @Component
@Slf4j
public class IdleAGVFilter extends RequestFilterAdapter<AGVRequest,AgvResponse> {


    @Override
    public AgvResponse request(AGVContext context, NextFilter<AGVRequest, AgvResponse> nextFilter, AGVRequest request) throws Exception {

        log.info("IdleAGVFilter | try to move idle agv");

        return super.request(context, nextFilter, request);
    }
}
