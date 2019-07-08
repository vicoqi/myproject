package com.vic.mychain.implfilter;

import com.vic.mychain.filter.MessageFilterAdapter;
import com.vic.mychain.message.IMessage;
import com.vic.mychain.message.FirstMessage;
import com.vic.mychain.start.StartMessage;

/**
 * Created by wang on 2019/7/2.
 */
public class FirstFilter extends MessageFilterAdapter<StartMessage,FirstMessage>{
    @Override
    public void request(NextFilter nextFilter, IMessage request) throws Exception {
        FirstMessage firstMessage = new FirstMessage();
        firstMessage.setCode("1");
        firstMessage.setType("1.1");
        super.request(nextFilter, firstMessage);
    }
}
