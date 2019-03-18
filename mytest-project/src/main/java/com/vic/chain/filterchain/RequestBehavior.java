package com.vic.chain.filterchain;

import com.vic.chain.AGVContext;
import com.vic.chain.Request;

public interface RequestBehavior<P extends Request, R> {

    void setHandler(RequestHandler<P, R> handler);

    RequestHandler<P, R> getHandler();

    R execute(AGVContext context, P param);
}

