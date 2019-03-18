package com.vic.chain.handler;

import com.vic.chain.AGVContext;
import com.vic.chain.AGVRequest;
import com.vic.chain.AgvResponse;
import com.vic.chain.filterchain.RequestHandler;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author robert
 * @Description AGV上报消息的最终处理者，完成AGV路段锁闭申请、释放
 * @Date 下午5:07 2018/6/19
 * @Param
 * @return
 **/
//@Component
@Slf4j
public class AGVMessageHandler implements RequestHandler<AGVRequest, AgvResponse> {

	@Override
	public void init() throws Exception {
		log.info("request | AGVMessageHandler |init");
	}

	@Override
	public AgvResponse request(AGVContext context, AGVRequest request) throws Exception {
		log.info("request | AGVMessageHandler | agvId ");
		return AgvResponse.SUCCESS;
	}

	public static void main(String[] args) {
	}
}
