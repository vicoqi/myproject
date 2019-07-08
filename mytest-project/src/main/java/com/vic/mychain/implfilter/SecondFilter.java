package com.vic.mychain.implfilter;

import com.vic.mychain.filter.MessageFilterAdapter;
import com.vic.mychain.message.FirstMessage;
import com.vic.mychain.message.IMessage;

/**
 * Created by wang on 2019/7/2.
 */
public class SecondFilter extends MessageFilterAdapter{
    @Override
    public void request(NextFilter nextFilter, IMessage request) throws Exception {
        FirstMessage firstMessage = new FirstMessage();
        firstMessage.setCode("2");
        firstMessage.setType("2.2");
        super.request(nextFilter, firstMessage);
    }
}
