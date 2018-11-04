package com.vic.chain.filter;

import com.vic.chain.AGVContext;
import com.vic.chain.AGVRequest;
import com.vic.chain.AgvResponse;
import com.vic.chain.filterchain.RequestFilterAdapter;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class PathRePlanFilter extends RequestFilterAdapter<AGVRequest, AgvResponse> {

	@Override
	public AgvResponse request(AGVContext context, NextFilter<AGVRequest, AgvResponse> nextFilter, AGVRequest request)
			throws Exception {
		log.info("PathRePlanFilter | try to move idle agv");
		return AgvResponse.SUCCESS;
	}
}
