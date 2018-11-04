package com.vic.chain.start;

import com.vic.chain.AGVContext;
import com.vic.chain.AGVMessage;
import com.vic.chain.AGVRequest;
import com.vic.chain.MessageBehavior;

/**
 * @Date: 2018/10/31 18:15
 * @Description:
 */
public class StartMain {
    public static void main(String[] args){
        MessageBehavior messageBehavior = new MessageBehavior();
        AGVContext context = new AGVContext();
        AGVRequest request = new AGVRequest(new AGVMessage());
        messageBehavior.execute(context,request);

    }
}
