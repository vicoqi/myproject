package com.vic.annotate.service.impl;


import com.vic.annotate.HandlerType;
import com.vic.annotate.service.IHandleService;
import org.springframework.stereotype.Component;

@Component
@HandlerType("one")
public class HandleOneServiceImpl implements IHandleService {
    public void handleService() {
        System.out.println("handle one service");
    }
}
