package com.vic.annotate.processor;

import com.vic.annotate.service.IHandleService;
import com.vic.annotate.util.BeanTool;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: 处理器上下文，根据类型获取相应的处理器
 * @Date: Created in 10:07 2019/2/2
 */
@Component
@SuppressWarnings("unchecked")
public class HandlerContext {

    private Map<String, Class> handlerMap;

    public HandlerContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public IHandleService getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found handler for type: " + type);
        }
        return (IHandleService) BeanTool.getBean(clazz);
    }

}
