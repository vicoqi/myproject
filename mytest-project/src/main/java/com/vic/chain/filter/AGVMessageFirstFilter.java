package com.vic.chain.filter;

import com.vic.chain.AGVContext;
import com.vic.chain.AGVRequest;
import com.vic.chain.AgvResponse;
import com.vic.chain.filterchain.RequestFilterAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 过滤 旧的AGVMessage（UDP报文可能乱序到达），并更新AGV 当前context，做些并发控制
 */
//@Component
@Slf4j
public class AGVMessageFirstFilter extends RequestFilterAdapter<AGVRequest, AgvResponse> {

	@Override
	public AgvResponse request(AGVContext context, NextFilter<AGVRequest, AgvResponse> nextFilter, AGVRequest request)
			throws Exception {
			log.error("AGVMessageFirstFilter | empty message");
		return AgvResponse.SUCCESS;
	}
}
