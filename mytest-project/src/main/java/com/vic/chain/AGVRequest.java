/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.vic.chain;

import lombok.Getter;
import lombok.Setter;

/**
* @author robert
* @Description AGV上报的消息
* @Date 下午12:03 2018/6/19
* @Param
* @return
**/
@Getter @Setter
public class AGVRequest implements Request{

    /** LCS上报的AGV消息 */
    private AGVMessage agvMessage;
    /** AGV当前位置 */
    private Integer currentLocation;

    public AGVRequest(AGVMessage agvMessage) {
        this.agvMessage = agvMessage;
    }
}
