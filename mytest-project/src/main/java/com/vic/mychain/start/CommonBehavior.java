
package com.vic.mychain.start;

import com.vic.mychain.chain.DefaultMessageFilterChain;
import com.vic.mychain.chain.DefaultRequestFilterChainBuilder;
import com.vic.mychain.chain.MessageFilterChain;
import com.vic.mychain.message.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;


public abstract class CommonBehavior{

    private static final Logger logger = LoggerFactory.getLogger(CommonBehavior.class);
//    private final MessageFilterChain filterChain =
//        new DefaultMessageFilterChain(this);
    private final MessageFilterChain filterChain =
            new DefaultMessageFilterChain();

//    @PostConstruct
    public void init() {
        DefaultRequestFilterChainBuilder builder =
            new DefaultRequestFilterChainBuilder();
        try {
            this.buildRequestChain(builder);
            builder.buildFilterChain(filterChain);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public void execute(IMessage request) {
        try {
            filterChain.fireRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    protected abstract void buildRequestChain(DefaultRequestFilterChainBuilder builder);

    protected abstract String chainType();


}
