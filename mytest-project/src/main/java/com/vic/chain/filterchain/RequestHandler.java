package com.vic.chain.filterchain;

import com.vic.chain.AGVContext;
import com.vic.chain.Request;

public interface RequestHandler<P extends Request, R> {
    void init() throws Exception;
    R request(AGVContext context, P request) throws Exception;
}

