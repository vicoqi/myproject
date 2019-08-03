package com.vic.mychain.implfilter;

import com.vic.mychain.filter.MessageFilterAdapter;
import com.vic.mychain.message.FirstMessage;
import com.vic.mychain.message.IMessage;
import com.vic.mychain.message.SecondeMessage;
import com.vic.mychain.start.StartMessage;

/**
 * Created by wang on 2019/7/2.
 */
public class SecondFilter extends MessageFilterAdapter<FirstMessage, SecondeMessage>{
    @Override
    public void request(NextFilter nextFilter, IMessage request) throws Exception {
        SecondeMessage secondeMessage = new SecondeMessage();
        secondeMessage.setCode("2");
        secondeMessage.setType("2.2");
        if(request instanceof FirstMessage){
            secondeMessage.setType("232");
        }
        super.request(nextFilter, secondeMessage);
    }
}
