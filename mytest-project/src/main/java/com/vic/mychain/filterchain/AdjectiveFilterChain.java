package com.vic.mychain.filterchain;

import com.vic.mychain.domain.ChainContext;
import com.vic.mychain.domain.RequestMess;
import com.vic.mychain.domain.Response;

public abstract class AdjectiveFilterChain implements FilterChain<RequestMess, Response>{


    @Override
    public void addFirst(String name, RequestFilter<RequestMess, Response> filter) {

    }

    @Override
    public void addLast(String name, RequestFilter<RequestMess, Response> filter) {

    }

    @Override
    public void clear() throws Exception {

    }

    @Override
    public Response fireRequest(ChainContext context, RequestMess request) throws Exception {
        return null;
    }
}
